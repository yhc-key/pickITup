import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Image from "next/image";
import Link from "next/link";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "pick IT up",
  description: "pick your IT information and recruit",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="kr">
      <body className={inter.className}>
        <header className="flex justify-between">
          <div>
            <Link href="/">
              <Image src="/pickITup.svg" alt="logo" width="200" height="1" />
            </Link>
          </div>
          <div className="">
            <Link href="/recruit" className="mr-4 text-blue-500">채용공고</Link>
            <Link href="/techBlog" className="mr-4 text-blue-500">기술 블로그</Link>
            <Link href="/game" className="mr-4 text-blue-500">미니 게임</Link>
            <Link href="/interview" className="mr-4 text-blue-500">면접 대비</Link>
          </div>
          <div>
            로그인 | 회원가입
          </div>
        </header>
        <main >{children}</main>
        <footer className="flex justify-evenly bg-gray-400 text-white h-16">
          <div className="py-4">Copyright © 2024. SSAFY 특화 A_406 F5 세희고침</div>
          <div className="py-4">서비스 이용약관</div>
          <div className="py-4">개인 정보 처리방침</div>
          <div className="py-4">Gitlab</div>
        </footer>
      </body>
    </html>
  );
}
