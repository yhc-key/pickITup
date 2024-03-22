"use client";
import { useSearchParams, useRouter } from "next/navigation";
import { Fragment, Suspense, useEffect } from "react";
import useAuthStore,{AuthState} from "@/store/authStore";

function Search() {
  const searchParams = useSearchParams();
  const refreshToken = searchParams.get("refresh-token") ?? "";
  const accessToken = searchParams.get("access-token") ?? "";
  const expiresIn = searchParams.get("expires_in") ?? "";
  if (refreshToken !== "") {

    sessionStorage.setItem("accessToken", accessToken);
    sessionStorage.setItem("refreshToken", refreshToken);
    sessionStorage.setItem("expiresIn", expiresIn);
  }

  console.log(searchParams.toString);

  console.log(accessToken);
  console.log(refreshToken);
  console.log(expiresIn);
  return <Fragment>hi</Fragment>;
}

export default function Callback() {
  const router = useRouter();
  const login:(nickname : string) => void = useAuthStore((state : AuthState) => state.login);


  // useEffect(() => {
  //   fetch("https://spring.pickitup.online/users/me",{
  //     method:"GET",
  //     headers: {
  //       Authorization: "Bearer "+accessToken
  //     },
  //   })
  //   .then(res=>res.json())
  //   .then(res=>{
  //     sessionStorage.setItem('authid',res.response.id);
  //     sessionStorage.setItem('nickname',res.response.nickname);
  //     login(sessionStorage.getItem('nickname')??'');
  //   })
  //   .catch(e=>{alert(e)});
  //   router.push("/");
  // }, [ router]);
  return (
    <Suspense>
      <Search />
    </Suspense>
  );
}

// export default function Callback() {
// }
