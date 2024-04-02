"use client";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Fragment } from "react";
import { useMediaQuery } from "react-responsive";

export default function InfoLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const pathname = usePathname();
  const isActive = (path: string) => path === pathname;

  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  return (
    <Fragment>
      <div
        className={`border-b-2 border-black flex flex-row items-center  ${isMobile ? "w-full mt-3" : "mr-4 ml-16"}`}
      >
        <div
          className={`w-1/4 h-16 flex items-center justify-center transition-all ease-in duration-200 hover:text-f5green-300 ${isActive("/main/myPage/myFavoriteRecruit") ? "border-t-4 border-f5green-300 text-f5green-300 font-semibold" : ""}`}
        >
          <Link href="/main/myPage/myFavoriteRecruit">
            <span>{isMobile ? "찜한 공고" : "내가 찜한 채용공고"}</span>
          </Link>
        </div>
        <div
          className={`w-1/4 h-16 flex items-center justify-center transition-all ease-in duration-200 hover:text-f5green-300 ${isActive("/main/myPage/myBadge") ? "border-t-4 border-f5green-300 text-f5green-300 font-semibold" : ""}`}
        >
          <Link href="/main/myPage/myBadge">
            <span>나의 뱃지</span>
          </Link>
        </div>
        <div
          className={`w-1/4 h-16 flex items-center justify-center transition-all ease-in duration-200 hover:text-f5green-300 ${isActive("/main/myPage/myPastAns") ? "border-t-4 border-f5green-300 text-f5green-300 font-semibold" : ""}`}
        >
          <Link href="/main/myPage/myPastAns">
            <span>{isMobile ? "과거 문제" : "나의 과거 문제 내역"}</span>
          </Link>
        </div>
        <div
          className={`w-1/4 h-16 flex items-center justify-center transition-all ease-in duration-200 hover:text-f5green-300 ${isActive("/main/myPage/myEssay") ? "border-t-4 border-f5green-300 text-f5green-300 font-semibold" : ""}`}
        >
          <Link href="/main/myPage/myEssay">
            <span>{isMobile ? "자소서" : "자기소개서 관리"}</span>
          </Link>
        </div>
      </div>
      <div className={`flex-grow ${isMobile ? "" : "ml-16"}`}>{children}</div>
    </Fragment>
  );
}
