"use client";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Fragment } from "react";

export default function InfoLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const pathname = usePathname();
  const isActive = (path: string) => path === pathname;
  return (
    <Fragment>
      <div className="border-b-2 border-black flex flex-row items-center mr-4">
        <Link
          href="/myPage/myFavoriteRecruit"
          className={`w-1/4 text-f5black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myFavoriteRecruit") ? "border-t-4 border-f5green-300" : ""} `}
        >
          <span>내가 찜한 채용공고</span>
        </Link>
        <Link
          href="/myPage/myBadge"
          className={`w-1/4 text-f5black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myBadge") ? "border-t-4 border-f5green-300" : ""} `}
        >
          <span>나의 뱃지</span>
        </Link>
        <Link
          href="/myPage/myPastAns"
          className={`w-1/4 text-f5black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myPastAns") ? "border-t-4 border-f5green-300" : ""} `}
        >
          <span>나의 과거 문제 내역</span>
        </Link>
        <Link
          href="/myPage/myEssay"
          className={`w-1/4 text-f5black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myEssay") ? "border-t-4 border-f5green-300" : ""} `}
        >
          <span>자기소개서 관리</span>
        </Link>
      </div>
      <div className="flex-grow ml-5">{children}</div>
    </Fragment>
  );
}
