"use client"
import { useEffect, useState, ReactElement } from "react";
import Image from 'next/image';
import { badgeDataMap } from "@/data/badgeData";
import TechSelectAfterLogin from "@/components/techSelectAfterLogin";
function MyBadge() {
  let acquired:string[] = ["attend1","jobRead1","jobRead50","jobScrap1","attend50","attend100"]; //얻은 배지 정보
  let unacquired:string [] = ["selfDocWrite1","selfDocWrite50","selfDocWrite100",
  "game10","game30","game50","game150","game300","jobRead100","jobScrap50","jobScrap100"];  //얻지 못한 배지 정보
  
  const [acq,setAcq] = useState<ReactElement[]>([]); //react 문 보낼것
  const [unacq,setUnacq] = useState<ReactElement[]>([]); //react 문 보낼것

  useEffect(() => {
    const newElements:ReactElement[] = [];
    for (let i = 0; i < acquired.length; i++) {
      newElements.push(
        <div key={i} className="flex flex-col items-center justify-center h-32 w-32 mx-4">
          <div className="flex items-center justify-center">
            <Image src={`/images/badge/${acquired[i]}.png`} width={100} height={100} alt={`${acquired[i]}`}/>
          </div>
          <div className="flex items-center justify-center text-sm font-bold">{badgeDataMap.get(acquired[i])}</div>
        </div>
      );
    }
    setAcq(newElements);
  }, []);

  useEffect(() => {
    const newElements:ReactElement[] = [];
    for (let i = 0; i < unacquired.length; i++) {
      newElements.push(
        <div key={i} className="flex flex-col items-center justify-center h-32 w-32 mx-4">
          <div className="h-[100px] w-[100px] flex items-center justify-center">
            {/* <Image src={`/images/badge/${unacquired[i]}.png`} width={100} height={100} alt="badge"/> */}
            <Image src="/images/badge/locked.png" width={50} height={50} alt="badge"/>
          </div>
          <div className="flex items-center justify-center text-sm font-bold">{badgeDataMap.get(unacquired[i])}</div>
        </div>
      );
    }
    setUnacq(newElements);
  }, []);

  return (
    <div>
      <div className="flex items-center justify-start mt-6">
        <div className="h-12 w-48 bg-[#CBFFC2] flex items-center justify-center rounded-lg font-bold">내가 획득한 뱃지</div>
      </div>
      <div className="flex items-center justify-start flex-wrap w-full">
        {acq}
      </div>
      <div className="flex items-center justify-start mt-6">
        <div className="h-12 w-48 bg-[#CBFFC2] flex items-center justify-center rounded-lg font-bold">획득 가능한 뱃지</div>
      </div>
      <div className="flex items-center justify-start flex-wrap w-full">
        {unacq}
      </div>
      <TechSelectAfterLogin/>
    </div>
  )
}
export default MyBadge;