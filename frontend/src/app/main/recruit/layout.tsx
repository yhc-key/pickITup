"use client";

import Image from "next/image";
import Link from "next/link";
import useAuthStore, { AuthState } from "@/store/authStore";
import { techDataMap } from "@/data/techData";
import { useEffect, useState } from "react";
import { FaSearch } from "react-icons/fa";
import TanstackProvider from "@/providers/TanstackProvider";

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

export default function RecruitLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const [nowType, setNowType] = useState("언어");
  const [pickTechList, setPickTechList] = useState<string[]>([]);
  const [techs, setTechs] = useState<string[]>(techDataMap.get("언어") ?? []);

  const changeTechTypeHandler = (techType: string) => {
    setNowType(techType);
    let techsTmp: string[] = [...(techDataMap.get(techType) || [])];
    pickTechList.forEach((s) => {
      const index: number = techsTmp?.indexOf(s) ?? -1;
      if (index !== -1) {
        techsTmp?.splice(index, 1);
      }
    });
    setTechs(techsTmp);
  };

  const techClickHandler = (tech: string) => {
    setPickTechList([...pickTechList, tech]);
    const index: number = techs?.indexOf(tech) ?? -1;
    if (index !== -1) {
      techs?.splice(index, 1);
    }
  };

  const techDeleteHandler = (tech: string) => {
    setPickTechList((prevTechList) =>
      prevTechList.filter((item) => item !== tech)
    );
  };

  useEffect(() => {
    let techsTmp: string[] = [...(techDataMap.get(nowType) || [])] ?? [];
    pickTechList.forEach((s) => {
      const index: number = techsTmp?.indexOf(s) ?? -1;
      if (index !== -1) {
        techsTmp?.splice(index, 1);
      }
    });
    setTechs(techsTmp);
  }, [pickTechList]);

  return (
    <div className="flex mx-10 my-5">
      <div className="min-w-[330px] max-w-[330px] my-5">
        <div className="flex w-full justify-center my-3 items-center text-center gap-2 bg-f5gray-300 h-10 rounded-md text-f5gray-500 text-sm">
          <FaSearch /> 검색 조건을 입력해주세요
        </div>
        <div className="flex flex-wrap gap-x-2 gap-y-1">
          {pickTechList.map((pickTech: string, index: number) => {
            return (
              <button
                type="button"
                onClick={() => techDeleteHandler(pickTech)}
                key={index}
                className="flex flex-row items-center p-1 text-sm border border-f5gray-300 rounded-2xl hover:bg-f5red-200"
              >
                <Image
                  src={`/images/techLogo/${pickTech}.png`}
                  alt={pickTech}
                  width="28"
                  height="28"
                  className="w-auto"
                />
              </button>
            );
          })}
        </div>
        <div className="flex flex-wrap gap-x-4 gap-2 mt-3">
          {techTypes.map((techType: string, index: number) => {
            const isActive: boolean = nowType == techType;
            return (
              <button
                type="button"
                onClick={(): void => changeTechTypeHandler(techType)}
                className={`border border-f5gray-300 rounded-3xl p-2 my-0 hover:bg-f5green-200 text-sm ${isActive ? "border-f5green-400" : ""}`}
                key={index}
              >
                {techType}
              </button>
            );
          })}
        </div>
        <div className="flex flex-wrap gap-x-4 gap-y-2 mt-3 max-w-[1000px]">
          {techs?.map((tech: string, index: number) => {
            return (
              <button
                type="button"
                key={index}
                onClick={() => techClickHandler(tech)}
                className="flex flex-row items-center p-1 text-sm border border-f5gray-300 rounded-2xl hover:bg-f5green-200"
              >
                <Image
                  src={`/images/techLogo/${tech}.png`}
                  alt={tech}
                  width="28"
                  height="28"
                  className="w-auto"
                />
              </button>
            );
          })}
        </div>
      </div>
      <TanstackProvider>
        <div className="flex-grow ml-5">{children}</div>
      </TanstackProvider>
    </div>
  );
}
