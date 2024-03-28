"use client";

import Image from "next/image";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import useAuthStore, { AuthState } from "../store/authStore";
import { useMediaQuery } from "react-responsive";
import { LinkType } from "@/type/interface";

const navLinks: LinkType[] = [
  { name: "채용공고", href: "/main/recruit", icon: "📆" },
  // { name: "기술블로그", href: "/main/techBlog" },
  { name: "미니 게임", href: "/main/game", icon: "🎮" },
  { name: "면접 대비", href: "/main/interview", icon: "📝" },
  { name: "마이 페이지", href: "/main/myPage/myBadge", icon: "💻" },
];

export default function Header() {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });
  const nickname: string = useAuthStore((state: AuthState) => state.nickname);
  const setLogged: (nickname: string) => void = useAuthStore(
    (state: AuthState) => state.setLogged
  );
  const isLoggedIn: boolean = useAuthStore(
    (state: AuthState) => state.isLoggedIn
  );
  const logout: () => void = useAuthStore((state: AuthState) => state.logout);

  useEffect(() => {
    const accessToken: string | null = sessionStorage.getItem("accessToken");
    const nickname: string | null = sessionStorage.getItem("nickname");

    if (accessToken !== null && nickname !== null) {
      setLogged(nickname);
      console.log();
    }
  }, []);

  const router = useRouter();
  const pathname = usePathname();
  const isActive = (path: string) => path === pathname;
  const logoutRequest = () => {
    if (isLoggedIn === true) {
      const token = sessionStorage.getItem("accessToken");
      fetch("https://spring.pickitup.online/auth/logout", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + token,
        },
      })
        .then((res) => res.json())
        .then((res) => {
          console.log(res);

          if (res.success === false) {
            alert(res.error.message);
          } else if (res.success === true) {
            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
            sessionStorage.removeItem("expiresIn");
            sessionStorage.removeItem("authid");
            sessionStorage.removeItem("nickname");
          }
        })
        .catch((e) => {
          alert(e);
        });
      logout();

      router.push("/main/recruit");
    }
  };
  return (
    <header>
      <div>
        <div className="py-1 flex justify-between border-b border-f5gray-400 mb:hidden">
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
              <div className="mx-1 text-lg font-semibold text-f5green-300">
                IT
              </div>
              <div className="mx-1 text-lg font-semibold text-f5black-400">
                up
              </div>
            </Link>
          </div>
          <div className="flex">
            {navLinks.map((link: LinkType) => {
              return (
                <div key={link.name} className="m-auto">
                  <Link
                    href={link.href}
                    className={`mr-4  hover:text-f5green-300 transition-all ease-in duration-300 ${
                      !isActive(link.href)
                        ? "text-f5black-400"
                        : "text-f5green-300 font-semibold"
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
                className="transition-all duration-200 ease-in text-f5black-400 hover:text-f5green-300"
              >
                로그인 | 회원가입
              </Link>
            </div>
          )}
        </div>
        {isMobile && (
          <div className="fixed flex w-[100%] bottom-0 left-0 pt-2 z-20 shadow-inner bg-white">
            {navLinks.map((link: LinkType) => {
              return (
                <div key={link.name} className="mx-auto ">
                  <Link
                    href={link.href}
                    className={` hover:text-f5green-300 transition-all ease-in duration-300 ${
                      !isActive(link.href)
                        ? "text-f5black-400"
                        : "text-f5green-400 font-bold transition-all ease-in duration-300"
                    }`}
                  >
                    <div className="text-lg text-center"> {link.icon}</div>
                    <div className="text-sm text-center"> {link.name}</div>
                  </Link>
                </div>
              );
            })}
          </div>
        )}
      </div>
    </header>
  );
}
