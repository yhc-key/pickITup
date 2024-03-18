"use client";
import { useState } from "react";
import Image from "next/image";
import Link from "next/link";

import Modal from "../modal";
import { techDataMap } from "@/../data/techData";

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

  const techs: string[] | undefined = techDataMap.get(pickType);

  return (
    <div>
      <button onClick={() => setIsModalOpen(true)}>
        <Image
          src="/images/OXQuiz.png"
          alt="OXQuiz"
          width={400}
          height={280}
          priority={true}
          className="transition-all ease-in-out hover:-translate-y-1 hover:scale-105  duration-500"
        />
      </button>
      <Modal open={isModalOpen}>
        <div className="flex flex-col flex-wrap">
          <div className="text-xl font-medium text-center mb-5">
            🎮 게임 주제를 선택해주세요 🎮
          </div>
          <div className="text-center text-sm mb-5">{pickTech}</div>
          <div className="flex  justify-center flex-wrap gap-2 mt-3">
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
          <div className="border-t-2 m-4"></div>
          <div className="min-h-[250px]">
            <div className="flex flex-wrap justify-center gap-4">
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
                      src={`/Images/techLogo/${tech}.png`}
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
          <div className="flex justify-center mt-5">
          <button onClick={modalCloseHandler} className="px-12 py-2 mr-6 text-neutral-100 text-sm font-semibold rounded-md bg-f5red-350  hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10">취소하기</button>
            <Link href={`/game/OXQuiz/${pickTech}`}>
            <button className="px-12 py-2 text-neutral-100 text-sm font-semibold rounded-md bg-f5green-350  hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10">시작하기</button>
            </Link>
          </div>
        </div>
      </Modal>
    </div>
  );
}
