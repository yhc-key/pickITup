"use client";
import { useState } from "react";
import Image from "next/image";
import Link from "next/link";

import { techDataMap } from "@/data/techData";

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

export default function InterViewPage() {
  const [pickType, setPickType] = useState("언어");
  const [pickTech, setPickTech] = useState("");

  // 기술스택 선택 함수
  const techClickHandler = (tech: string): void => {
    setPickTech(tech);
  };

  const techs: string[] | undefined = techDataMap.get(pickType);

  return (
    <div>
      <div className="flex flex-wrap justify-center mx-auto mt-4 mb-4">
        <div className="flex flex-col justify-evenly">
          <div className="flex items-center justify-center text-5xl font-semibold tracking-wider">
            <Image
              src="/images/pencil.png"
              alt="pencil"
              width={150}
              height={150}
              priority={true}
            />
            <div className="my-2 mr-1 text-f5green-300">면접 대비 </div>
            <div className="my-2 ml-2 text-f5black-400">QUIZ</div>
          </div>
          <div className="flex flex-col items-center justify-center">
            <div className="font-medium text-f5black-400">
              분야별로 면접 빈출 질문을 제공합니다!
            </div>
            <div className="font-medium text-f5black-400">
              시간 내에 답변을 생각하는 연습과 예시답변을 참고하여 면접을
              대비해보세요
            </div>
          </div>
        </div>
      </div>
      <div className="flex flex-col flex-wrap justify-center">
      <div className="flex justify-center my-5">
          <Link href={`/main/interview/${pickTech}`}>
            <button className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10">
              시작하기
            </button>
          </Link>
        </div>
        <div className="flex flex-wrap justify-center gap-2 mt-3">
          {techTypes.map((techType: string, index: number) => {
            const isActive: boolean = pickType == techType;
            return (
              <button
                type="button"
                onClick={(): void => setPickType(techType)}
                className={`border border-f5gray-300 rounded-2xl text-f5black-400 text-xs p-2  hover:transition-all hover:scale-105 hover:ease-in-out  ${isActive ? "border-f5green-300 border-2 scale-105" : ""}`}
                key={index}
              >
                {techType}
              </button>
            );
          })}
        </div>
        <div className="w-1/2 mx-auto my-4 border-t-2 "></div>
        <div className= "min-h-[200px]">
          <div className="flex flex-wrap justify-center w-1/2 gap-4 mx-auto">
            {techs?.map((tech: string, index: number) => {
              const isActive: boolean = pickTech == tech;
              return (
                <button
                  type="button"
                  key={index}
                  onClick={() => techClickHandler(tech)}
                  className={`flex flex-row border-f5gray-300 border py-1 pr-2 rounded-2xl text-f5black-400 text-xs items-center  hover:transition-all hover:scale-105 hover:ease-in-out  ${isActive ? "border-f5green-300 border-2 scale-105" : ""}`}
                >
                  <Image
                    src={`/images/techLogo/${tech}.png`}
                    alt={tech}
                    width={22}
                    height={22}
                    className="mx-1"
                  />
                  {tech}
                </button>
              );
            })}
          </div>
        </div>
   
      </div>
    </div>
  );
}
