#- coding: utf-8 -#
from OpenSSL import crypto
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad, pad
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization

def demo_main():
    ticketlen = 2048

    # 生成秘钥对
    key = crypto.PKey()
    key.generate_key(crypto.TYPE_RSA, 2048) # 这里使用的是 RSA_F4，不过应该没有影响。比 RSA_3 安全性更高，但性能更差

    # 拿公钥. 这里 pub_key 可能要转 str
    pub_key = crypto.dump_publickey(crypto.FILETYPE_PEM, key)

    # 调接口
    res = iOASSO_auth_secure("omg.mnet2.com", "quick_login", "tenterm", "2.0.3", pub_key, ticketlen)
    ticketinfo = res["ticketinfo"]
    aeskey = res["aeskey"]

    # 这里非对称解密出 aeskey，用 RSA_PKCS1_PADDING
    pri_key = key.to_cryptography_key()
    decryptedKey = pri_key.decrypt(aeskey, padding.PKCS1v15())

    print("aeskey: %s, decryptedKey: %s", aeskey, decryptedKey)

    # 对称解密
    para1 = decrypt(ticketinfo, decryptedKey)

    print(para1)
    pass


def iOASSO_auth_secure(url: str, appid: str, appname: str, appversion: str, enckey: bytes, ticketlen: int) -> dict:
    # 原始的aeskey
    aeskey_raw = "11111111111111111111111111111111"
    # 返回的数据
    res = "131231343434"
    
    # aes 256 cbc 给数据对称加密
    cipher = AES.new(aeskey_raw.encode(), AES.MODE_CBC)
    pad_pkcs7 = pad(res.encode(), AES.block_size) # 填充
    ticketinfo = cipher.encrypt(pad_pkcs7)

    # aeskey 用公钥加密
    key = serialization.load_pem_public_key(enckey, default_backend())
    aeskey = key.encrypt(aeskey_raw.encode(), padding.PKCS1v15())

    return {"aeskey": aeskey, "ticketinfo": ticketinfo}


def decrypt(ticketinfo: str, decryptedKey: bytes) -> str:
    # 解密
    cipher = AES.new(decryptedKey, AES.MODE_CBC)
    plaintext = cipher.decrypt(ticketinfo)

    print("plaintext: {}, ticketinfo: {}", plaintext, ticketinfo)

    # 移除填充
    plaintext = unpad(plaintext, AES.block_size)

    return plaintext.decode()


if __name__ == "__main__":
    demo_main()