"use client";
import Image from "next/image";

import { FaCheck } from "react-icons/fa6";
import { techDataMap } from "@/../data/techData";
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

  const changeTechTypeHandler = (techType : string) => {
    setNowType(techType);
    let techsTmp : string[] = [...(techDataMap.get(techType) || [] )];
    pickTechList.forEach(s  => {
      const index : number = techsTmp?.indexOf(s) ?? -1;
      if (index !== -1) {
        techsTmp?.splice(index, 1);
      }
    })
    setTechs(techsTmp);
  }
  const techClickHandler = (tech:string) => {
    setPickTechList([...pickTechList, tech])
    const index : number = techs?.indexOf(tech) ?? -1;
    if (index !== -1) {
      techs?.splice(index,1);
    }
  }
  const techDeleteHandler = (tech: string) => {
    setPickTechList(prevTechList => prevTechList.filter(item => item !== tech));
  }

  const duplClickHandler = () => {};

  useEffect(() => {
    let techsTmp : string[] = [...(techDataMap.get(nowType) || [] )] ?? [];
    pickTechList.forEach(s  => {
      const index : number = techsTmp?.indexOf(s) ?? -1;
      if (index !== -1) {
        techsTmp?.splice(index, 1);
      }
    })
    setTechs(techsTmp)
  }, [pickTechList])

  return ( 
    <form
      onSubmit={submitHandler}
      className="border border-f5gray-500 rounded-2xl h-full py-6 pl-20 flex flex-col relative">
      <h2 className="font-bold text-2xl">정보 수정하기</h2>
      <Image
        src="/Images/pickItup.svg"
        alt="프로필사진"
        width="100"
        height="100"
        className="rounded-full my-5 h-auto"
      />
      <div className="flex flex-row items-center relative">
        <div className="absolute">
          닉네임 :
        </div>
        <input placeholder="조싸피" className="flex items-center p-2 border rounded-lg bg-f5gray-400 ml-20 w-1/3 min-w-80 h-8" />
        <button
          type="button"
          onClick={duplClickHandler}
          className="ml-4 h-8 text-f5black-400 border rounded-lg bg-f5gray-400 hover:bg-f5gray-500 font-bold px-2 flex flex-row items-center gap-2"
        >
          <FaCheck />
          중복 체크
        </button>
      </div>
      <p className="text-f5green-400 text-sm ml-20 mt-1">
        사용 가능한 닉네임입니다.
      </p>
      <div className="flex flex-wrap mt-4 items-center min-h-12 gap-2 max-w-[1000px]">
      <span className="font-bold">
      추가될 기술 스택 :
      </span>       
        {pickTechList.map((pickTech: string, index : number )=> {
        return <button type="button" onClick={() => techDeleteHandler(pickTech)} key={index} className="flex flex-row border-f5gray-300 border p-1 pr-2 rounded-2xl text-sm items-center hover:bg-f5red-200">
        <Image src={`/Images/techLogo/${pickTech}.png`}
          alt={pickTech} width="28" height="28" className="mr-1 w-auto"/>
          {pickTech}
        </button>
      })}
      </div>
      <div className="flex flex-wrap gap-4 mt-3">
        {techTypes.map((techType: string, index: number) => {
          const isActive:boolean = nowType == techType
          return <button type="button" onClick={(): void => changeTechTypeHandler(techType)} className={`border border-f5gray-300 rounded-3xl p-2 hover:bg-f5green-200 ${isActive ? "border-f5green-400" : ""}`} key={index}>
            {techType}
          </button>
        })}
      </div>
      <div className="flex flex-wrap gap-4 mt-3 max-w-[1000px]">
        {techs?.map((tech: string, index: number) => {
         return <button type="button" key={index} onClick={() => techClickHandler(tech)} className="flex flex-row border-f5gray-300 border p-1 pr-2 rounded-2xl text-sm items-center hover:bg-f5green-200">
            <Image src={`/Images/techLogo/${tech}.png`}
              alt={tech} width="28" height="28" className="mr-1 w-auto"/>
              {tech}
              </button>
        })}
      </div>
      <div className="flex flex-row items-center relative mt-3">
        <div className="absolute">
          Github :
        </div>
        <input placeholder="https://github.com/yhc-key" className="flex items-center p-2 border rounded-lg bg-f5gray-400 ml-20 w-1/3 min-w-80 h-8" />
      </div>
      <div className="flex flex-row items-center relative mt-3">
        <div className="absolute">
          Velog :
        </div>
        <input placeholder="https://velog.io/@yhc-key" className="flex items-center p-2 border rounded-lg bg-f5gray-400 ml-20 w-1/3 min-w-80 h-8" />
      </div>
      <div className="flex items-center justify-center p-2 rounded-l ml-20 w-1/3 min-w-80 h-8"> or </div>
      <div className="flex flex-row items-center relative ">
        <div className="absolute">
          Tistory :
        </div>
        <input placeholder="" disabled  className="flex items-center p-2 border rounded-lg bg-f5gray-400 ml-20 w-1/3 min-w-80 h-8" />
      </div>
      <div className="flex flex-row items-center relative mt-3">
        <div className="absolute">
          Email :
        </div>
        <input placeholder="yhcho0712@gmail.com" className="flex items-center p-2 border rounded-lg bg-f5gray-400 ml-20 w-1/3 min-w-80 h-8" />
      </div>



      <div className="absolute bottom-0 right-0 mr-6 mb-6">
        <button type="submit" className="bg-f5red-300 rounded-lg text-white mx-4 py-2 px-6">
          취소하기
          </button> 
        <button type="reset" className="bg-f5green-300 rounded-lg text-white mx-4 py-2 px-6">
          등록하기
          </button> 
      </div>
    </form>
  );
}