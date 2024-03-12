"use client";
import Image from "next/image";

import { FaCheck } from "react-icons/fa6";
import { techDataMap } from "@/../data/techData";
import { useState } from "react";

const techTypes: string[] = [
  "언어",
  "프론트앤드",
  "백앤드",
  "모바일",
  "데이터",
  "데브옵스",
  "테스팅툴",
  "정보보안",
];



export default function MyPage() {
  const [nowType, setNowType] = useState("언어");
  const submitHandler = () => {};
  const duplClickHandler = () => {};

  const techs = techDataMap.get(nowType);
  return (
    <form
      onSubmit={submitHandler}
      className="border border-f5gray-500 rounded-2xl h-full py-6 pl-20 flex flex-col"
    >
      <h2 className="font-bold text-2xl">정보 수정하기</h2>
      <Image
        src="/Images/pickItup.svg"
        alt="프로필사진"
        width="100"
        height="100"
        className="rounded-full my-5"
      />
      <div className="flex flex-row items-center">
        닉네임 :{" "}
        <div className="flex items-center p-2 border rounded-lg bg-f5gray-400 mx-4 w-1/3 min-w-20 h-8">
          조싸피
        </div>
        <button
          type="button"
          onClick={duplClickHandler}
          className="h-8 text-f5black-400 border rounded-lg bg-f5gray-400 hover:bg-f5gray-500 font-bold px-2 flex flex-row items-center gap-2"
        >
          <FaCheck />
          중복 체크
        </button>
      </div>
      <p className="text-f5green-400 text-sm ml-20 mt-1">
        사용 가능한 닉네임입니다.
      </p>
      <div className="flex flex-wrap mt-4 font-bold">추가될 기술 스택 :</div>
      <div className="flex flex-wrap gap-4 mt-3">
        {techTypes.map((techType: string, index: number) => {
          return <button type="button" onClick={(): void => setNowType(techType)}className="border border-f5gray-300 rounded-3xl p-2 hover:bg-f5green-200" key={index}>
            {techType}
          </button>
        })}
      </div>
      <div className="flex flex-wrap gap-4 mt-3 max-w-[600px]">
        {techs?.map((tech: string, index: number) => {

         return <button key={index}>
            <Image src={`/Images/techLogoEx/${tech}.png`}
              alt={tech}
              width="60"
              height="7"
              className="w-auto h-7" />
        </button>})}
      </div>
    </form>
  );
}
