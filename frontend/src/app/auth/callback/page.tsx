"use client"
import { useSearchParams,useRouter } from "next/navigation";
import { useEffect } from "react";
import useAuthStore from "../../../../store/authStore";
export default function Callback(){
  const router = useRouter();
  const login = useAuthStore(state => state.login);
  const searchParams = useSearchParams();
  useEffect(() => {
    const refreshToken = searchParams.get('refresh-token') ?? '';
    const accessToken = searchParams.get('access-token') ?? '';
    const tokenType = searchParams.get('token_type') ?? '';
    const expiresIn = searchParams.get('expires_in') ?? '';
    sessionStorage.setItem('tokenType',tokenType);
    sessionStorage.setItem('accessToken',accessToken);
    sessionStorage.setItem('refreshToken',refreshToken);
    sessionStorage.setItem('expiresIn',expiresIn);
    console.log(searchParams.toString);
    
    console.log(tokenType);
    console.log(accessToken);
    console.log(refreshToken);
    console.log(expiresIn);

    // fetch("https://spring.pickitup.online/users/me",{
    //   method:"GET",
    //   headers: {
    //     Authorization: `${sessionStorage.getItem('tokenType')} ${sessionStorage.getItem('accessToken')}`
    //   },
    // })
    // .then(res=>res.json())
    // .then(res=>{
    //   sessionStorage.setItem('authid',res.response.id);
    //   sessionStorage.setItem('nickname',res.response.nickname);
    //   login(sessionStorage.getItem('nickname')??'');
    // })
    // .catch(e=>{alert(e)});


    router.push('/');
  }, [searchParams, router]);
  return(
    <>hi</>
  )
}