"use client";
import { useRouter } from "next/navigation";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import useAuthStore, { AuthState } from "@/store/authStore";
function Login() {
  const router = useRouter();
  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const login: (nickname: string) => void = useAuthStore(
    (state: AuthState) => state.login
  );
  const setKeywords: (newKeywords: string[]) => void = useAuthStore(
    (state: AuthState) => state.setKeywords
  );
  const keywords: string[] = useAuthStore((state: AuthState) => state.keywords);

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
            const token=res.response.accessToken;
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
              router.push("/main/recruit");
            })
            .catch((error) => {
              alert(error);
            });
            fetch(`https://spring.pickitup.online/users/keywords`,{
              method : "GET",
              headers:{
                "Authorization":"Bearer "+token,
              }
            })
            .then(res=>res.json())
            .then(res=>{
              setKeywords(res.response.keywords);
              sessionStorage.setItem('keywords',JSON.stringify(res.response.keywords));
              console.log(keywords);
            })
          }
        })
        .catch((error) => {
          alert("아이디 혹은 비밀번호가 일치하지 않습니다." + error);
          return;
        });
    }
  };
  return (
    <div className="flex flex-col justify-center items-center w-full mt-10">
      <div className="min-w-[400px] mx-auto mb:min-w-[350px]">
        <div className="flex items-center justify-center mb-12 text-xl font-bold text-f5black-400">
          pick IT up 로그인
        </div>
        <div>
          <form>
            <div className="flex flex-col gap-7">
              <div>
                <div className="flex w-full justify-start items-center mb-2">
                  <label htmlFor="id" className=" text-f5black-400 text-sm">
                    아이디
                  </label>
                </div>
                <div>
                  <input
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                    placeholder="아이디를 입력해주세요"
                    type="text"
                    required
                    className="w-full rounded-lg min-h-12 border border-f5gray-400 appearance-none px-4 placeholder:text-f5gray-400 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300"
                  />
                </div>
              </div>
              <div>
                <div className="flex w-full justify-start items-center mb-2">
                  <label htmlFor="id" className=" text-f5black-400 text-sm">
                    비밀번호
                  </label>
                </div>
                <div>
                  <input
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="비밀번호를 입력하세요"
                    type="password"
                    required
                    className="w-full rounded-lg min-h-12 border border-f5gray-400 appearance-none px-4 placeholder:text-f5gray-400 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300"
                  />
                </div>
              </div>
              <div>
                <button
                  type="submit"
                  onClick={(e) => {
                    e.preventDefault(), requestLogin();
                  }}
                  className="w-full rounded-md bg-f5green-300 text-white font-bold min-h-12"
                >
                  로그인
                </button>
              </div>
            </div>
          </form>
        </div>
        <div className="flex flex-col items-center justify-center whitespace-pre border-t-[1px] border-gray-400 mt-12">
         <p className="text-f5black-400 bg-white relative px-4 -top-3 translate-x-0.5 translate-y-0.5 text-xs">또는</p>
          <Link href="/main/signup" className="w-full mt-8">
            <button  className="w-full rounded-md border text-f5green-300 font-bold min-h-12">
          이메일로 회원가입
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
}
export default Login;
