"use client";

import Image from "next/image";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import useAuthStore from "../store/authStore";
interface LinkType {
  name: string;
  href: string;
}

const navLinks: LinkType[] = [
  { name: "채용공고", href: "/recruit" },
  { name: "기술블로그", href: "/techBlog" },
  { name: "미니 게임", href: "/game" },
  { name: "면접 대비", href: "/interview" },
  { name: "마이 페이지", href: "/myPage/myBadge" },
];

export default function Header() {
  const nickname = useAuthStore((state) => state.nickname);
  const isLoggedIn = useAuthStore((state) => state.isLoggedIn);
  const logout = useAuthStore((state) => state.logout);
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [tokenType, setTokenType] = useState<string | null>(null);

  useEffect(() => {
    setAccessToken(sessionStorage.getItem("accessToken"));
    setTokenType(sessionStorage.getItem("tokenType"));
  }, []);

  const router = useRouter();
  const pathname = usePathname();
  const isActive = (path: string) => path === pathname;
  const logoutRequest = () => {
    fetch("https://spring.pickitup.online/auth/logout", {
      method: "POST",
      headers: {
        Authorization: tokenType + " " + accessToken,
      },
    });
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("tokenType");
    sessionStorage.removeItem("expiresIn");
    sessionStorage.removeItem("authid");
    sessionStorage.removeItem("nickname");
    logout();

    router.push("/");
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
        <div className="p-3 my-auto mr-10 bg-f5gray-300 rounded-2xl">
          <Link
            href="/social"
            className="text-f5black-400 hover:text-f5green-300"
          >
            로그인 & 회원가입
          </Link>
        </div>
      )}
    </header>
  );
}
