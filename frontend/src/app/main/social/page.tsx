"use client";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";

function Social() {
  const requsetKakaoLogin = () => {
    window.location.href =
      "https://spring.pickitup.online/oauth2/authorization/kakao";
  };
  const requsetNaverLogin = () => {
    window.location.href =
      "https://spring.pickitup.online/oauth2/authorization/naver"; //url 집어넣기
  };
  const requsetGoogleLogin = () => {
    window.location.href =
      "https://spring.pickitup.online/oauth2/authorization/google"; //url 집어넣기
  };

  const messages = [
    "픽잇업이 제공하는 서비스를",
    "하나의 계정으로 모두 이용할 수 있습니다!",
  ];
  return (
    <div className="flex flex-col items-center w-full mt-10 mb:mt-32">
      <div className="grid place-items-center">
        <div className="flex flex-col items-center justify-center">
          {messages.map((line, index) => (
            <div
              className="my-1 text-2xl text-f5black-400 font-semibold mb:text-xl"
              key={index}
            >
              {line}
            </div>
          ))}
        </div>
        <div className="mt-16 flex flex-col items-center">
          <button
            onClick={requsetKakaoLogin}
            className="w-96 h-12 py-5 my-4 flex items-center justify-center rounded-xl bg-[#feeb00] whitespace-pre font-bold mb:w-80"
          >
            <Image
              src="/images/kakaoLogo.png"
              width={40}
              height={40}
              alt="kakaoLogo"
              className="mr-3"
            />
            카카오 계정으로 로그인
          </button>
          <button
            onClick={requsetNaverLogin}
            className="w-96 h-12 my-4 flex items-center justify-center rounded-xl text-white bg-[#03C75A] whitespace-pre font-bold mb:w-80"
          >
            <Image
              src="/images/naverLogo.png"
              width={40}
              height={40}
              alt="naverLogo"
              className="mr-3"
            />
            네이버 계정으로 로그인
          </button>
          <button
            onClick={requsetGoogleLogin}
            className="w-96 h-12 my-4 pl-6 flex items-center justify-center rounded-xl border border-f5gray-400 whitespace-pre font-bold mb:w-80"
          >
            <Image
              src="/images/googleLogo.png"
              width={21}
              height={21}
              alt="googleLogo"
              className="mr-6"
            />
            Google 계정으로 로그인
          </button>
          <Link
            href="/main/login"
            className="w-96 h-12 my-4 pr-3 flex items-center justify-center rounded-xl border border-f5gray-400 whitespace-pre font-bold mb:w-80"
          >
            <Image
              src="/images/pickITupLogo.png"
              width={24}
              height={21.84}
              alt="pickITupLogo"
              className="mr-[1.7rem]"
            />
            pick IT up 로그인
          </Link>
        </div>
      </div>
    </div>
  );
}
export default Social;
