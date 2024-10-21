#- coding: utf-8 -#
# 本地内存缓存
import time
import functools
from heapq import nsmallest
from operator import itemgetter
from collections import OrderedDict

class ZeroDict(dict):
    def __missing__(self, key):
        return 0

def memoize_lfu(maxtime=0, maxsize=10000):
    """
    @maxtime: 缓存时间，单位秒
    @maxsize: 缓存大小
    """
    def decorating_function(user_function):
        cache = {}
        count = ZeroDict()
        @functools.wraps(user_function)
        def wrapper(*args, **kwargs):
            key = args + tuple(sorted(kwargs.items())) if kwargs else args

            if maxtime and wrapper.stamp != time.time() // maxtime:
                cache.clear() # 过期了，删除 chache
                wrapper.stamp = time.time() // maxtime

            try:
                result = cache[key]
                wrapper.hits += 1
            except KeyError:
                # 超过了，需要删点数据
                if len(cache) >= maxsize:
                    # 10求余，最少为1
                    n, fn = maxsize // 10 or 1, itemgetter(1)
                    # 这里用堆排，根据 count 的值来删除最少的几个 key
                    for k, _ in nsmallest(n, count.iteritems(), key=fn):
                        del cache[k], count[k]
                    
                result = cache[key] = user_function(*args, **kwargs)
                wrapper.misses += 1

            count[key] += 1
            return result

        def clear():
            cache.clear()
            count.clear()
            wrapper.stamp = wrapper.hits = wrapper.misses = 0

        wrapper.stamp = wrapper.hits = wrapper.misses = 0
        wrapper.clear = clear
        wrapper.cache = cache
        return wrapper
    return decorating_function


def memoize_lru(maxtime=0, maxsize=10000):
    '''Least-recently-used cache decorator.

    Arguments to the cached function must be hashable.
    Cache performance statistics stored in f.hits and f.misses.
    http://en.wikipedia.org/wiki/Cache_algorithms#Least_Recently_Used

    @param maxtime: maxinum seconds of elements to keep
    @param maxsize: maximum number of elements to keep in cache
    '''
    def decorating_function(user_function):
        cache = OrderedDict() # order: least recent to most recent
        @functools.wraps(user_function)
        def wrapper(*args, **kwargs):
            key = args + tuple(sorted(kwargs.items())) if kwargs else args
            if maxtime and wrapper.stamp != time.time() // maxtime:
                cache.clear() # expired, clear the cache
                wrapper.stamp = time.time() // maxtime
            try:
                result = cache.pop(key)
                wrapper.hits += 1
            except KeyError:
                if len(cache) >= maxsize:
                    cache.popitem(0) # purge least recently used cache entry
                result = user_function(*args, **kwargs)
                wrapper.misses += 1
            cache[key] = result      # record recent use of this key
            return result
        wrapper.stamp = wrapper.hits = wrapper.misses = 0
        return wrapper
    return decorating_function


if __name__ == "__main__":
    from random import choice

    @memoize_lfu(maxsize=20)
    def lfu(x, y):
        return 3 * x + y

    domain = range(5)
    start = time.time()
    for i in range(10000):
        lfu(choice(domain), choice(domain))
    print('lfu', time.time() - start, lfu.hits, lfu.misses)