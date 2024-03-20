"use client";
import Image from "next/image";

import { FaCheck } from "react-icons/fa6";
import { techDataMap } from "@/data/techData";
import { useEffect, useState } from "react";

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
  const [pickTechList, setPickTechList] = useState<string[]>([]);
  const [techs, setTechs] = useState<string[]>(techDataMap.get("언어") ?? []);
  const submitHandler = () => {};

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

  const duplClickHandler = () => {};

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
    <form
      onSubmit={submitHandler}
      className="relative flex flex-col h-full py-6 pl-20 border border-f5gray-500 rounded-2xl"
    >
      <h2 className="text-2xl font-bold">정보 수정하기</h2>
      <Image
        src="/Images/pickItup.svg"
        alt="프로필사진"
        width="100"
        height="100"
        className="h-auto my-5 rounded-full"
      />
      <div className="relative flex flex-row items-center">
        <div className="absolute">닉네임 :</div>
        <input
          placeholder="조싸피"
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
        <button
          type="button"
          onClick={duplClickHandler}
          className="flex flex-row items-center h-8 gap-2 px-2 ml-4 font-bold border rounded-lg text-f5black-400 bg-f5gray-400 hover:bg-f5gray-500"
        >
          <FaCheck />
          중복 체크
        </button>
      </div>
      <p className="mt-1 ml-20 text-sm text-f5green-400">
        사용 가능한 닉네임입니다.
      </p>
      <div className="flex flex-wrap mt-4 items-center min-h-12 gap-2 max-w-[1000px]">
        <span className="font-bold">추가될 기술 스택 :</span>
        {pickTechList.map((pickTech: string, index: number) => {
          return (
            <button
              type="button"
              onClick={() => techDeleteHandler(pickTech)}
              key={index}
              className="flex flex-row items-center p-1 pr-2 text-sm border border-f5gray-300 rounded-2xl hover:bg-f5red-200"
            >
              <Image
                src={`/Images/techLogo/${pickTech}.png`}
                alt={pickTech}
                width="28"
                height="28"
                className="w-auto mr-1"
              />
              {pickTech}
            </button>
          );
        })}
      </div>
      <div className="flex flex-wrap gap-4 mt-3">
        {techTypes.map((techType: string, index: number) => {
          const isActive: boolean = nowType == techType;
          return (
            <button
              type="button"
              onClick={(): void => changeTechTypeHandler(techType)}
              className={`border border-f5gray-300 rounded-3xl p-2 hover:bg-f5green-200 ${isActive ? "border-f5green-400" : ""}`}
              key={index}
            >
              {techType}
            </button>
          );
        })}
      </div>
      <div className="flex flex-wrap gap-4 mt-3 max-w-[1000px]">
        {techs?.map((tech: string, index: number) => {
          return (
            <button
              type="button"
              key={index}
              onClick={() => techClickHandler(tech)}
              className="flex flex-row items-center p-1 pr-2 text-sm border border-f5gray-300 rounded-2xl hover:bg-f5green-200"
            >
              <Image
                src={`/Images/techLogo/${tech}.png`}
                alt={tech}
                width="28"
                height="28"
                className="w-auto mr-1"
              />
              {tech}
            </button>
          );
        })}
      </div>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">Github :</div>
        <input
          placeholder="https://github.com/yhc-key"
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">Velog :</div>
        <input
          placeholder="https://velog.io/@yhc-key"
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>
      <div className="flex items-center justify-center w-1/3 h-8 p-2 ml-20 rounded-l min-w-80">
        {" "}
        or{" "}
      </div>
      <div className="relative flex flex-row items-center ">
        <div className="absolute">Tistory :</div>
        <input
          placeholder=""
          disabled
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">Email :</div>
        <input
          placeholder="yhcho0712@gmail.com"
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>

      <div className="absolute bottom-0 right-0 mb-6 mr-6">
        <button
          type="submit"
          className="px-6 py-2 mx-4 text-white rounded-lg bg-f5red-300"
        >
          취소하기
        </button>
        <button
          type="reset"
          className="px-6 py-2 mx-4 text-white rounded-lg bg-f5green-300"
        >
          등록하기
        </button>
      </div>
    </form>
  );
}
