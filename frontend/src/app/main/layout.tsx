"use client";
import type { Metadata } from "next";
import { Noto_Sans_KR } from 'next/font/google';
import "../globals.css";

import Header from "@/components/header";
import Footer from "@/components/footer";

const signLinks = [
  { name: "로그인", href: "/login" },
  { name: "회원가입", href: "/signUp" },
];

// export const metadata: Metadata = {
//   title: "pick IT up",
//   description: "pick your IT information and recruit",
// };

const noto = Noto_Sans_KR({
  subsets: ['latin'], // 또는 preload: false
});

export default function MainLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
      <body className={`${noto.className} min-h-screen flex flex-col`}>
        <Header />
        <main className="flex-grow mb:mb-24">{children}</main>
        <div id="globalModal"></div>
        <Footer />
      </body>
  );
}