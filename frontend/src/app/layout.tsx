"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Header from "../../components/header";

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
      <body className={inter.className}>
        <Header />

        <main>{children}</main>
        <footer className="bg-gray-400 text-white flex justify-evenly h-16">
          <div>Copyright © 2024. SSAFY 특화 A_406 F5 세희고침</div>
          <div>서비스 이용약관</div>
          <div>개인 정보 처리방침</div>
          <div>Gitlab</div>
        </footer>
      </body>
    </html>
  );
}
