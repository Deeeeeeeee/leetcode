// Demo.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "openssl/rsa.h"
#include "openssl/pem.h"
#include "openssl/err.h"


/*
安全版本，向iOA请求票据，iOA认证组件会校验exe的数字签名，没有受信任的发行者tencent签名，则直接返回错误，正确则向服务器请求票据，根据
iOA返回的票据信息判断是否需要用户二次确认，需要确认的等用户确认后再返回给应用，票据加密使用aes-256。
[in]url 请求的url
[in]appid 应用id
[in]appname 应用名称
[in]appversion 应用版本
[in]enckey 应用生成一对非对称加密密钥，rsa-2048，公钥传给ioa，ioa用来加密加密票据的key
[out]ticketinfo 由调用者申请存储空间，返回票据信息，预定义格式，使用aes-256加密，<app id>|<ticket>|<valid period start time offset>|<valid period end time offset>|<validtimes>|<user name>|<device id>|<error code>|<app name>|<app ver>|<req url>
[in|out]ticketlen ticketinfo长度，如果传入的存储区长度不够，则设置为需要的长度
[out]aeskey 由调用者申请存储空间，长度256byte，iOA组件生成的密钥，RSA加密后的密文，密钥enckey

return int 
0 successful 
-1 参数有错误
-2 缓冲区大小不够

int IOA_SSO_API iOASSOAuthSecure(char* url, char* appid, char* appname, char* appversion, const char *enckey, char* ticketinfo, int *ticketlen, char* aeskey);


通知iOA票据是否使用
hash 票据的md5
used 是否使用

void IOA_SSO_API iOASSOAuthPost(char* hash, bool used);
*/

typedef void(*SyncRequest)(const char* url, int urllen, char** ticketinfo, int *ticketlen);
typedef std::string (*Decode) (std::string key, const std::string ticket);
typedef void (*Term) ();
typedef int (*iOASSOAuthSecureFunc)(char* url, char* appid, char* appname, char* appversion, const char *enckey, char* ticketinfo, int *ticketlen, char* aeskey);

void handleErrors(void)
{
    ERR_print_errors_fp(stderr);
    //abort();
}

int decrypt(unsigned char *ciphertext, int ciphertext_len, unsigned char *key,
            unsigned char *iv, unsigned char *plaintext)
{
    EVP_CIPHER_CTX *ctx;

    int len;

    int plaintext_len;

    /* Create and initialise the context */
    if(!(ctx = EVP_CIPHER_CTX_new()))
        handleErrors();

    /*
     * Initialise the decryption operation. IMPORTANT - ensure you use a key
     * and IV size appropriate for your cipher
     * In this example we are using 256 bit AES (i.e. a 256 bit key). The
     * IV size for *most* modes is the same as the block size. For AES this
     * is 128 bits
     */
    if(1 != EVP_DecryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, iv))
        handleErrors();

    /*
     * Provide the message to be decrypted, and obtain the plaintext output.
     * EVP_DecryptUpdate can be called multiple times if necessary.
     */
    if(1 != EVP_DecryptUpdate(ctx, plaintext, &len, ciphertext, ciphertext_len))
        handleErrors();
    plaintext_len = len;

    /*
     * Finalise the decryption. Further plaintext bytes may be written at
     * this stage.
     */
    if(1 != EVP_DecryptFinal_ex(ctx, plaintext + len, &len))
        handleErrors();
    plaintext_len += len;

    /* Clean up */
    EVP_CIPHER_CTX_free(ctx);

    return plaintext_len;
}

int _tmain(int argc, _TCHAR* argv[])
{


	std::wstring dllPath = L"C:\\Program Files (x86)\\iOA";
	dllPath.append(L"\\iOASSO.dll");
	HMODULE lib = LoadLibrary(dllPath.c_str());
	DWORD dw = GetLastError();

	if (lib != NULL)
	{
		iOASSOAuthSecureFunc secureFunc;
		secureFunc = (iOASSOAuthSecureFunc)GetProcAddress(lib, "iOASSOAuthSecure");
		char* ticketinfo = new char[2048];
		memset(ticketinfo, 2048, 0);
		int ticketlen = 2048;
		//funcSyncRequest((char*)url.c_str(), url.length(),&ticketinfo, &ticketlen);
		BIGNUM *bn = BN_new();
		BN_set_word(bn, RSA_3);
		RSA *rsa = RSA_new();
		int ret = RSA_generate_key_ex(rsa, 2048, bn, NULL);
		if (ret != 1)
		{
			printf("generate key error\n");
		}

	//	BIO *pri = BIO_new(BIO_s_mem());
		BIO *pub = BIO_new(BIO_s_mem());
		//PEM_write_bio_RSAPrivateKey(pri, rsa, NULL, NULL, 0, NULL, NULL);
		PEM_write_bio_RSAPublicKey(pub, rsa);

		size_t pub_len = BIO_pending(pub);
		//size_t pri_len = BIO_pending(pri);
		char *pub_key = (char *)malloc(pub_len + 1);
		//char *pri_key = (char *)malloc(pri_len + 1);
		//BIO_read(pri, pri_key, pri_len);
		BIO_read(pub, pub_key, pub_len);
		pub_key[pub_len] = '\0';
		//pri_key[pri_len] = '\0';
		char aeskey[256];

		//quick_login_5D2
		ret = secureFunc("omg.mnet2.com", "quick_login", "tenterm", "2.0.3", pub_key, ticketinfo, &ticketlen, aeskey);
		if (ret == 0)
		{
			int len = RSA_size(rsa);  
			char *decryptedKey = (char *)malloc(len + 1);  
			memset(decryptedKey, 0, len + 1);
			ret = RSA_private_decrypt(len, (const unsigned char*)aeskey, (unsigned char*)decryptedKey, rsa, RSA_PKCS1_PADDING);
			if (ret == -1)
			{
				unsigned long error = ERR_get_error();
				char buf[128];
				memset(buf, 0, 128);
				ERR_error_string(error, buf);
				printf("%s\n", buf);
			}

			char *decryptedText = (char *)malloc(ticketlen+1);  
			memset(decryptedText, 0, ticketlen+1);
			len = decrypt((unsigned char *)ticketinfo, ticketlen, (unsigned char *)decryptedKey, NULL, (unsigned char *)decryptedText);

			std::string para1 = std::string(decryptedText, len);
			if(NULL!=ticketinfo)
			{
				delete []ticketinfo;
			}
			printf("get ticket %s\n", para1.c_str());

			//Sleep(2000*1000);
			//funcTerm();
			FreeLibrary(lib);
			getchar();
		}
	}

	return 0;
}