"use client";
import { useSearchParams, useRouter } from "next/navigation";
import { Fragment, Suspense, useEffect } from "react";
import useAuthStore, { AuthState } from "@/store/authStore";
import Swal from "sweetalert2";
function Search() {
  const login: (nickname: string) => void = useAuthStore(
    (state: AuthState) => state.login
  );
  const router = useRouter();
  const searchParams = useSearchParams();
  const refreshToken = searchParams.get("refresh-token") ?? "";
  const accessToken = searchParams.get("access-token") ?? "";
  const expiresIn = searchParams.get("expires_in") ?? "";
  if (accessToken !== "") {
    sessionStorage.setItem("accessToken", accessToken);
    sessionStorage.setItem("refreshToken", refreshToken);
    sessionStorage.setItem("expiresIn", expiresIn);
    fetch("https://spring.pickitup.online/users/me", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + accessToken,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        sessionStorage.setItem("authid", res.response.id);
        sessionStorage.setItem("nickname", res.response.nickname);
        login(sessionStorage.getItem("nickname") ?? "");
      })
      .catch((e) => {
        Swal.fire({
          title: 'Error!',
          text: e,
          icon: 'warning',
          confirmButtonText: '확인',
          confirmButtonColor: '#00ce7c'
        })
      });
    router.push("/main/recruit");
  }

  return <Fragment>hi</Fragment>;
}

export default function Callback() {
  return (
    <Suspense>
      <Search />
    </Suspense>
  );
}

// export default function Callback() {
// }
