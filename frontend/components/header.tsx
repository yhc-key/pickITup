"use client";

import Image from "next/image";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { IoChevronDownSharp } from "react-icons/io5";
import { IoChevronUpSharp } from "react-icons/io5";
import { FiLogOut } from "react-icons/fi";
import { FiUsers } from "react-icons/fi";

import useAuthStore, { AuthState } from "../store/authStore";
import { useMediaQuery } from "react-responsive";
import { LinkType } from "@/type/interface";
import { navLinks } from "@/data/techData";
import CheckExpire from "@/data/checkExpire";

const apiAddress = "https://spring.pickITup.online/users/scraps/recruit";

export default function Header() {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const nickname: string = useAuthStore((state: AuthState) => state.nickname);
  const { setLogged, setBookmarks } = useAuthStore();
  const isLoggedIn: boolean = useAuthStore(
    (state: AuthState) => state.isLoggedIn
  );
  const logout: () => void = useAuthStore((state: AuthState) => state.logout);
  const profile: number = useAuthStore((state: AuthState) => state.profile);
  const [isSubMenuOpen, setIsSubMenuOpen] = useState<boolean>(false);

  useEffect(() => {
    CheckExpire();
    const accessToken: string | null = sessionStorage.getItem("accessToken");
    const nickname: string | null = sessionStorage.getItem("nickname");

    const fetchBookmarks = async () => {
      try {
        const res = await fetch(`${apiAddress}`, {
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        });
        const data = await res.json();
        // console.log("북마크fetch해옴");
        console.log(data);
        setBookmarks(data?.response);
      } catch (error) {
        console.error(error);
      }
    };

    if (accessToken !== null && nickname !== null) {
      setLogged(nickname);
      fetchBookmarks();
    } else {
      setBookmarks([]);
    }
  }, [setBookmarks, setLogged, isLoggedIn]);

  const router = useRouter();
  const pathname = usePathname();
  const isActive = (path: string) => path === pathname;
  const logoutRequest = () => {
    CheckExpire();
    setIsSubMenuOpen(false);
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
            sessionStorage.removeItem("expiresAt");
            sessionStorage.removeItem("authid");
            sessionStorage.removeItem("nickname");
            sessionStorage.removeItem("keywords");
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
        <div className="flex justify-between py-1 border-b border-f5gray-400 mb:hidden">
          <div>
            <Link href="/main/recruit" className="flex items-center">
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
              if (link.name === "마이 페이지" || link.name === "로그인") {
                return;
              }
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
            <div className="flex items-center my-auto mr-20">
              <div className="mr-2 text-sm text-f5black-400">{nickname}님</div>
              <div className="duration-300 ease-in cursor-pointer transion text-f5black-400 hover:text-f5green-300">
                {isSubMenuOpen ? (
                  <IoChevronUpSharp onClick={() => setIsSubMenuOpen(false)} />
                ) : (
                  <IoChevronDownSharp onClick={() => setIsSubMenuOpen(true)} />
                )}
              </div>
              {isSubMenuOpen ? (
                <div
                  className={`z-10 bg-white absolute h-24 w-36 top-16 right-16 rounded-lg shadow-md flex flex-col px-5 justify-center text-sm text-f5black-400 transition-colors duration-100 ease-in ${isSubMenuOpen ? "transition-opacity opacity-100 " : "transition-opacity opacity-0 hidden"}`}
                >
                  <div
                    onClick={() => setIsSubMenuOpen(false)}
                    className="flex items-center justify-start h-1/2"
                  >
                    <Link
                      href="/main/myPage/myBadge"
                      className="flex items-center justify-start transition duration-200 hover:text-f5green-300 hover:font-semibold"
                    >
                      <FiUsers className="mr-2" />
                      마이페이지
                    </Link>
                  </div>
                  <div
                    onClick={() => setIsSubMenuOpen(false)}
                    className="flex items-center justify-start h-1/2"
                  >
                    <button
                      className="flex items-center justify-start hover:text-f5green-300 hover:font-semibold"
                      onClick={logoutRequest}
                    >
                      <FiLogOut className="mr-2" />
                      로그아웃
                    </button>
                  </div>
                </div>
              ) : (
                <div></div>
              )}
            </div>
          ) : (
            <div className="p-3 my-auto mr-10">
              <Link
                href="/main/social"
                className="transition duration-200 ease-in-out text-f5black-400 hover:text-f5green-300 hover:font-semibold"
              >
                로그인 | 회원가입
              </Link>
            </div>
          )}
        </div>
        {isMobile && (
          <div className="fixed flex w-[100%] bottom-0 left-0 pt-2 z-20 shadow-inner bg-white">
            {navLinks.map((link: LinkType) => {
              if (link.name === "마이 페이지") {
                if (isLoggedIn) {
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
                } else {
                  return (
                    <div key={link.name} className="mx-auto ">
                      <Link
                        href="/main/social"
                        className={` hover:text-f5green-300 transition-all ease-in duration-300 ${
                          !isActive(link.href)
                            ? "text-f5black-400"
                            : "text-f5green-400 font-bold transition-all ease-in duration-300"
                        }`}
                      >
                        <div className="text-lg text-center">👋🏻</div>
                        <div className="text-sm text-center">로그인</div>
                      </Link>
                    </div>
                  );
                }
              }
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
