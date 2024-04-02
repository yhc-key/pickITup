export default function CheckExpire(): void{
  const expires = sessionStorage.getItem('expiresAt');
  if(expires !== null){
    const expiresAt = parseInt(expires,10);
    if(Date.now() > expiresAt){
      const token = sessionStorage.getItem('accessToken');
      const refresh = sessionStorage.getItem('refreshToken');
      fetch("https://spring.pickitup.online/auth/token/refresh",{
        method: "POST",
        headers: {
          Authorization: "Bearer "+token,
          "refresh-token": "Bearer "+refresh,
        }
      })
      .then(res=>res.json())
      .then(res=>{
        if(res.success === true){
          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
          sessionStorage.setItem("accessToken", res.response['access-token']);
          sessionStorage.setItem("refreshToken", res.response['refresh-token']);
          const expiresAt = Date.now() + 3000000;
          sessionStorage.setItem("expiresAt",expiresAt.toString());
        }
      })
    }
  }
}