"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Header from "../../components/header";
import { IoLogoGitlab } from "react-icons/io5";
const inter = Inter({ subsets: ["latin"] });

const signLinks = [
  { name: "로그인", href: "/login" },
  { name: "회원가입", href: "/signUp" },
];

// export const metadata: Metadata = {
//   title: "pick IT up",
//   description: "pick your IT information and recruit",
// };

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="kr">
      <body className={`${inter.className} min-h-screen flex flex-col`}>
        <Header />
        <main className="flex-grow">{children}</main>
        <footer className="mt-auto flex justify-evenly items-center bg-gray-400 text-white text-xs h-16">
          <div className="w-1/12"></div>
          <div>Copyright © 2024. SSAFY 특화 A_406 F5 세희고침</div>
          <div className="w-3/12"></div>
          <div>서비스 이용약관</div>
          <div>개인 정보 처리방침</div>
          <div><IoLogoGitlab className="inline-block"/> Gitlab</div>
          <div className="w-1/12"></div>
        </footer>
      </body>
    </html>
  );
}
