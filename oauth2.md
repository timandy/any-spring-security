# OAuth2 使用流程

### 浏览器访问
- response_type 有四种,默认使用 code
- client_id 为 appKey 在 AuthorizationServerConfiguration 控制,生产一般为 guid
- redirect_uri 为第三方的 url 地址,方便第三方接收 code
```
http://localhost:8080/oauth/authorize?response_type=code&client_id=android&redirect_uri=http://www.baidu.com
```

### 登录
- 用户密码为 basic 认证,在 WebSecurityConfiguration 控制
```
\\用户名
user

\\密码
123
```
### 同意授权
approve

### 返回 code
- url 地址栏返回 code
- code 只能使用一次,有效期较短 一般为 10 分钟

### 换取 token
- client_id 为授权时的 client_id
- client_secret 为 appSecret 在 AuthorizationServerConfiguration 控制,生产一般为 guid
- grant_type 为 authorization_code,使用 code 认证该值固定
- redirect_uri 为 授权时的 redirect_uri 必须完全一样
- code 为上一步返回的 code
- 访问以下地址返回 access_token
```
http://localhost:8080/oauth/token2?client_id=android&client_secret=pas&grant_type=authorization_code&redirect_uri=http://www.baidu.com&code=${code}
```
#### 访问保护资源
- access_token 为上一步返回的 access_token
- 使用 access_token 访问保护资源可以免登录
```
header 添加
Authorization:Bearer ${access_token}
访问
http://localhost:8080/print?value=hello+world!+%e4%bd%a0%e5%a5%bd%e4%b8%96%e7%95%8c!
```
