"use client";
import { useState } from "react";
import { useMediaQuery } from "react-responsive";
import Image from "next/image";
import Link from "next/link";

import Modal from "../../modal";
import { techDataMap } from "@/data/techData";

const techTypes: string[] = [
  "언어",
  "프론트엔드",
  "백엔드",
  "모바일",
  "데이터",
  "데브옵스",
  "테스팅툴",
];

export default function TechSelectOX() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [pickType, setPickType] = useState("언어");
  const [pickTech, setPickTech] = useState("");

  // 기술스택 선택 함수
  const techClickHandler = (tech: string) => {
    setPickTech(tech);
  };

  // 모달 닫는 함수
  const modalCloseHandler = () => {
    setPickType("언어");
    setPickTech("");
    setIsModalOpen(false);
  };

  const techs = techDataMap.get(pickType);

  return (
    <div>
      <button onClick={() => setIsModalOpen(true)}>
        <Image
          src="/images/speedQuiz.png"
          alt="OXQuiz"
          width={400}
          height={280}
          priority={true}
          className="transition-all duration-500 ease-in hover:-translate-y-1 hover:scale-105"
        />
      </button>
      <Modal open={isModalOpen}>
        <div className="flex flex-col flex-wrap">
          <div className="mb-5 text-xl font-medium text-center mb:text-base">
            🎮 게임 주제를 선택해주세요 🎮
          </div>
          <div className="mb-5 text-sm mb:text-xs text-center">{pickTech}</div>
          <div className="flex flex-wrap justify-center gap-2 mt-3">
            {techTypes.map((techType: string, index: number) => {
              const isActive: boolean = pickType == techType;
              return (
                <button
                  type="button"
                  onClick={(): void => setPickType(techType)}
                  className={`border border-f5gray-300 rounded-2xl text-f5black-400 text-xs p-2 mb:p-1 transition-all hover:scale-105 ease-in duration-150 ${isActive ? "border-f5green-300 border-2 scale-105" : ""}`}
                  key={index}
                >
                  {techType}
                </button>
              );
            })}
          </div>
          <div className="m-4 border-t-2"></div>
          <div className="min-h-[250px] mb:min-w-[260px]">
            <div className="flex flex-wrap justify-center gap-4 mb:gap-2">
              {techs?.map((tech: string, index: number) => {
                const techWithoutSpaces = tech.replace(/\s/g, ''); // 공백 제거
                const isActive: boolean = pickTech == tech;
                return (
                  <button
                    type="button"
                    key={index}
                    onClick={() => techClickHandler(tech)}
                    className={`flex flex-row border-f5gray-300 border py-1 pr-2 mb:pr-1 mb:py-0.5 rounded-2xl text-f5black-400 text-xs items-center transition-all ease-in duration-150 hover:scale-105 ${isActive ? "border-f5green-300 border-2 scale-105" : ""}`}
                  >
                    <Image
                      src={`/images/techLogo/${techWithoutSpaces}.png`}
                      alt={tech}
                      width={22}
                      height={22}
                      priority={true}
                      className="mx-1"
                    />
                    {tech}
                  </button>
                );
              })}
            </div>
          </div>
          <div className="flex flex-wrap gap-5 justify-center mt-5 mb:gap-1 ">
            <button
              onClick={modalCloseHandler}
              className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10 mb:mx-auto"
            >
              취소하기
            </button>
            <Link
              href={`/main/game/speedQuiz/${pickTech}`}
              className="mb:mx-auto mb:my-3"
            >
              <button
                className={`px-12 py-2 text-sm font-semibold rounded-md text-neutral-100  ring-1 ring-inset ring-f5green-700/10 ${pickTech ? "bg-f5green-350 cursor-pointer hover:bg-f5green-300 " : "bg-f5gray-500 cursor-not-allowed opacity-50"}`}
                disabled={!pickTech}
              >
                시작하기
              </button>
            </Link>
          </div>
        </div>
      </Modal>
    </div>
  );
}
