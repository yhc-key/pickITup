"use client";

import Image from "next/image";
import Link from "next/link";
import useAuthStore from "../../../store/authStore";
import { useEffect, useState } from "react";

const dummyMyData: string[][] = [
  ["내가 찜한 채용공고", "3 개", "/images/starOutline.png"],
  ["마감 임박 채용공고", "1 개", "/images/history.png"],
  ["문제 풀이 수", "64 개", "/images/iconLibraryBooks.png"],
  ["내 뱃지", "3 개", "/images/iconShield.png"],
];

export default function MyPageLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const [nickname, setNickname] = useState<string | null>(null);
  useEffect(() => {
    setNickname(sessionStorage.getItem("nickname"));
  }, []);
  return (
    <div className="flex mx-10 my-5">
      <div className="min-w-[330px] max-w-[330px]">
        <div className="flex flex-row justify-between">
          <Image
            src="/images/pickITup.svg"
            alt="dummyPerson"
            width="100"
            height="100"
            className="w-auto"
          />
          <div className="flex flex-col items-center gap-4">
            <p>{"nickname"}</p>
            <Link href="/myPage/updateMyInfo" className="flex flex-row">
              <Image
                src="/images/personEdit.png"
                alt="profileUpdate"
                width="100"
                height="100"
                className="w-auto mr-1"
              />
              내 정보 수정
            </Link>
          </div>
        </div>
        <div className="flex flex-row gap-4 my-4">
          <p>Level 7</p>
          <p>경험치 바~~</p>
        </div>
        <div className="border rounded-lg bg-f5green-200 px-4 py-2 my-4">
          {dummyMyData.map((data: string[], index: number) => {
            return (
              <div className="flex justify-between mt-3" key={index}>
                <div className="flex flex-row items-center gap-1">
                  <Image
                    src={data[2]}
                    width="20"
                    height="20"
                    alt="icon"
                    className="w-auto "
                  />
                  <p className="text-sm">{data[0]}</p>
                </div>
                <p className="text-sm">{data[1]}</p>
              </div>
            );
          })}
        </div>
        <div className="border border-f5gray-500 rounded-lg p-3">
          <p className="font-bold mb-2">내 기술 스택</p>
          <div className="flex flex-row flex-wrap gap-2">
            <div className="border border-f5gray-500 rounded-lg p-auto text-center min-w-16 h-7 flex items-center justify-center">
              프론트
            </div>
            <Image
              src="/images/techLogoEx/Angular.png"
              alt="앵귤러"
              width="80"
              height="7"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/ReactJS.png"
              alt="리액트"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/ReactNative.png"
              alt="리액트네이티브"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/VueJS.png"
              alt="뷰"
              width="80"
              height="10"
              className="w-auto h-7"
            />
          </div>
          <div className="flex flex-row flex-wrap mt-4 gap-2">
            <div className="border border-f5gray-500 rounded-lg p-auto text-center min-w-16 h-7 flex items-center justify-center">
              백앤드
            </div>
            <Image
              src="/images/techLogoEx/Java.png"
              alt="자바"
              width="80"
              height="7"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/Spring.png"
              alt="스프링"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/SpringBoot.png"
              alt="스프링부트"
              width="80"
              height="10"
              className="w-auto h-7"
            />
          </div>
          <div className="flex flex-row flex-wrap mt-4 gap-2">
            <div className="border border-f5gray-500 rounded-lg p-auto text-center w-20 h-7 flex items-center justify-center">
              DevOps
            </div>
            <Image
              src="/images/techLogoEx/Github.png"
              alt="깃허브"
              width="80"
              height="7"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/Docker.png"
              alt="도커"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/Kubernetes.png"
              alt="젠킨스"
              width="80"
              height="10"
              className="w-auto h-7"
            />
          </div>
          <div className="mt-4 flex flex-row gap-3 items-center">
            <Image
              src="/images/Github.png"
              alt="velog"
              width="30"
              height="30"
            />{" "}
            <a href="https://github.com/yhc-key">https://github.com/yhc-key</a>
          </div>
          <div className="mt-2 flex flex-row gap-3 items-center">
            <Image src="/images/velog.png" alt="velog" width="30" height="30" />{" "}
            <a href="http://velog.io/@yhc-key">https://velog.io/@yhc-key</a>
          </div>
          <div className="mt-2 flex flex-row gap-3 items-center">
            <Image src="/images/email.png" alt="velog" width="30" height="30" />{" "}
            yhcho0712@gmail.com
          </div>
        </div>
      </div>
      <div className="flex-grow ml-5">{children}</div>
    </div>
  );
}
