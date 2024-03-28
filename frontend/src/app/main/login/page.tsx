"use client";
import { useRouter } from "next/navigation";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import useAuthStore, { AuthState } from "@/store/authStore";
import TechSelectAfterLogin from "@/components/techSelectAfterLogin";
function Login() {
  const router = useRouter();
  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const login: (nickname: string) => void = useAuthStore(
    (state: AuthState) => state.login
  );
  const [accessToken, setAccessToken] = useState<string | null>(null);

  useEffect(() => {
    setAccessToken(sessionStorage.getItem("accessToken"));
  }, []);

  const requestLogin = () => {
    if (id.length === 0 || password.length === 0) {
      alert("아이디와 비밀번호를 입력해주세요!");
      return;
    } else {
      fetch("https://spring.pickitup.online/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: id,
          password: password,
        }),
      })
        .then((res) => res.json())
        .then((res) => {
          console.log(res);

          if (res.success === false) {
            alert(res.error.message);
            return;
          }
          if (res.success === true) {
            sessionStorage.setItem("accessToken", res.response.accessToken);
            sessionStorage.setItem("refreshToken", res.response.refreshToken);
            sessionStorage.setItem("expiresIn", "3600000");
            fetch("https://spring.pickitup.online/users/me", {
              method: "GET",
              headers: {
                Authorization: "Bearer " + res.response.accessToken,
              },
            })
              .then((res) => res.json())
              .then((res) => {
                sessionStorage.setItem("authid", res.response.id);
                sessionStorage.setItem("nickname", res.response.nickname);
                login(res.response.nickname);
                router.push("/main/myPage/myBadge");
              })
              .catch((error) => {
                alert(error);
              });
          }
        })
        .catch((error) => {
          alert("아이디 혹은 비밀번호가 일치하지 않습니다." + error);
          return;
        });
    }
  };
  return (
    <div className="flex flex-col justify-center items-center w-full h-[70vh]">
      <div className="flex items-center justify-center h-[10vh] text-xl font-bold">
        pick IT up 로그인
      </div>
      <div className="w-[45vw] h-[36vh] rounded-[10px] border border-f5gray-400">
        <form>
          <div className="flex w-full h-[6vh] justify-center items-center mt-14">
            <label htmlFor="id" className="w-[6vw] font-black">
              아이디
            </label>
            <input
              value={id}
              onChange={(e) => setId(e.target.value)}
              placeholder="아이디를 입력하세요"
              type="text"
              required
              className="w-[20vw] h-[5vh] ml-6 rounded-md border border-f5gray-400
            bg-gray-200 appearance-none pl-2
            text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300
            "
            />
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label htmlFor="password" className="w-[6vw] font-black">
              비밀번호
            </label>
            <input
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="비밀번호를 입력하세요"
              type="password"
              required
              className="w-[20vw] h-[5vh] ml-6 rounded-md border border-f5gray-400
            bg-gray-200 appearance-none pl-2
            text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300
            "
            />
          </div>

          <div className="flex w-full h-[14vh] justify-center items-center">
            <button
              type="submit"
              onClick={(e) => {
                e.preventDefault(), requestLogin();
              }}
              className="w-[18vw] h-[5vh] rounded-md bg-f5green-300 text-white text-lg font-bold"
            >
              로그인
            </button>
          </div>
        </form>
      </div>
      <div className="flex items-center justify-center h-[8vh] whitespace-pre">
        아직 계정이 없으신가요?{" "}
        <Link href="/main/signup" className="text-lg font-bold">
          회원가입 하러가기
        </Link>
      </div>
    </div>
  );
}
export default Login;
