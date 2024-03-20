"use client"
import { useSearchParams,useRouter } from "next/navigation";
import { useEffect } from "react";
export default function Callback(){
  const router = useRouter();
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

    router.push('/');
  }, [searchParams, router]);
  return(
    <>hi</>
  )
}