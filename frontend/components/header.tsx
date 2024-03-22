"use client";

import Image from "next/image";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import useAuthStore,{AuthState} from "../store/authStore";
interface LinkType {
  name: string;
  href: string;
}

const navLinks: LinkType[] = [
  { name: "채용공고", href: "/main/recruit" },
  { name: "기술블로그", href: "/main/techBlog" },
  { name: "미니 게임", href: "/main/game" },
  { name: "면접 대비", href: "/main/interview" },
  { name: "마이 페이지", href: "/main/myPage/myBadge" },
];

export default function Header() {

  const nickname :string = useAuthStore((state : AuthState) => state.nickname);
  const isLoggedIn :boolean = useAuthStore((state: AuthState) => state.isLoggedIn);
  const logout : () => void = useAuthStore((state : AuthState) => state.logout);
  const [accessToken, setAccessToken] = useState<string | null>(null);

  useEffect(() => {
    setAccessToken(sessionStorage.getItem("accessToken"));
  }, []);

  const router = useRouter();
  const pathname = usePathname();
  const isActive = (path: string) => path === pathname;
  const logoutRequest = () => {
    if(isLoggedIn===true){
      fetch("https://spring.pickitup.online/auth/logout", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + accessToken,
        },
      })
      .then(res=>res.json())
      .then(res=>{
        if(res.success===false){
          alert("로그아웃실패");
        }
        else if(res.success===true){
          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
          sessionStorage.removeItem("expiresIn");
          sessionStorage.removeItem("authid");
          sessionStorage.removeItem("nickname");
        }
      })
      .catch((e)=>{alert(e)});
      logout();

      router.push("/");
    }
  };
  return (
    <header className="flex justify-between border-b border-f5gray-400">
      <div>
        <Link href="/" className="flex items-center">
          <Image
            src="/images/ITUlogo.png"
            alt="logo"
            width={36}
            height={36}
            priority={true}
            className="m-3"
          />
          <div className="mx-1 text-lg font-semibold text-f5black-400">
            pick
          </div>
          <div className="mx-1 text-lg font-semibold text-f5green-300">IT</div>
          <div className="mx-1 text-lg font-semibold text-f5black-400">up</div>
        </Link>
      </div>
      <div className="flex">
        {navLinks.map((link: LinkType) => {
          return (
            <div key={link.name} className="m-auto">
              <Link
                href={link.href}
                className={`mr-4 hover:text-f5green-300 ${
                  !isActive(link.href)
                    ? "text-f5black-400"
                    : "text-f5green-400 font-bold"
                }`}
              >
                {link.name}
              </Link>
            </div>
          );
        })}
      </div>
      {isLoggedIn ? (
        <div className="flex items-center">
          <div className="mr-2">{nickname}님</div>
          <div className="p-3 my-auto mr-10 bg-f5gray-300 rounded-2xl">
            <button
              className="text-f5black-400 hover:text-f5green-300"
              onClick={logoutRequest}
            >
              로그아웃
            </button>
          </div>
        </div>
      ) : (
        <div className="p-3 my-auto mr-10 text-sm bg-f5gray-300 rounded-2xl">
          <Link
            href="/main/social"
            className="transition-all duration-200 ease-in-out text-f5black-400 hover:text-f5green-300"
          >
            로그인 | 회원가입
          </Link>
        </div>
      )}
    </header>
  );
}
