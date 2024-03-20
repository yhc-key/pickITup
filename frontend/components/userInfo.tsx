import { useEffect, useState } from "react";

export default function UserInfo(){

  const accessToken:any = sessionStorage.getItem('accessToken');
  console.log(accessToken);
  const tokenType:any = sessionStorage.getItem('tokenType');
  useEffect(()=>{
    fetch("https://spring.pickitup.online/users/me",{
      method:"GET",
      headers: {
        Authorization: `${tokenType} ${accessToken}`
      },
    })
    .then(res=>res.json())
    .then(res=>{
      // sessionStorage.setItem('authid',res.response.)
    })
    .catch(e=>{alert(e)});
  },[])
  const logout = () =>{
    fetch("https://spring.pickitup.online/auth/logout",{
      method: "POST",
      headers:{
        Authorization : `${tokenType} ${accessToken}`
      },
    })
  }
  return (
    <div>  
      <button onClick={logout}>로그아웃</button>
      userinfo
    </div>
  )
}