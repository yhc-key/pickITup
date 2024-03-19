"use client"
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
  return <Fragment>
  <div className="border-b-2 border-black flex flex-row items-center">
    <Link href="/myPage/info/myFavoriteRecruit" className={`w-1/4 text-f5 black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myFavoriteRecruit") ? "border-t-4 border-f5green-300" : ""} `}>
      <span>내가 찜한 채용공고</span>
    </Link>
    <Link href="/myPage/info/myBadge" className={`w-1/4 text-f5 black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myBadge") ? "border-t-4 border-f5green-300" : ""} `}>
      <span>나의 뱃지</span>
    </Link>
    <Link href="/myPage/info/myPastAns" className={`w-1/4 text-f5 black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myPastAns") ? "border-t-4 border-f5green-300" : ""} `}>
      <span>나의 과거 문제 내역</span>
    </Link>
    <Link href="/myPage/info/myEssay" className={`w-1/4 text-f5 black-300 h-16 flex items-center justify-center ${isActive("/myPage/info/myEssay") ? "border-t-4 border-f5green-300" : ""} `}>
      <span>자기소개서 관리</span>
    </Link>

  </div>
  <div className="flex-grow">{children}</div>
  </Fragment>

}

