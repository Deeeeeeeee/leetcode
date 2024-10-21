arr = [2, 3, 6, 7]
target = 7
path = []
res = []

def backtrace(start):
    if sum(path) > target:
        return
    if sum(path) == target:
        res.append(path.copy())
        return

    for i in range(start, len(arr)):
        path.append(arr[i])
        backtrace(i)
        path.pop()

backtrace(0)
print(res)