"use client"
import { useEffect, useState, ReactElement } from "react";
import Image from 'next/image';
function MyBadge() {
  let acquired:string[] = ["attend1","jobRead1","jobRead50","jobScrap1"]; //얻은 배지 정보
  let unacquired:string [] = ["attend50","attend100","blogRead1","blogRead50","blogRead100",
                              "blogScrap1","blogScrap50","blogScrap100","selfDocWrite1","selfDocWrite50","selfDocWrite100",
                              "game10","game30","game50","game150","game300","jobRead100","jobScrap50","jobScrap100"];  //얻지 못한 배지 정보
  const map = new Map<String,String>(); 
  map.set('attend1','출석 1일');
  map.set('attend50','출석 50일');
  map.set('attend100','출석 100일');
  map.set('blogRead1','Blog 열람 1회');
  map.set('blogRead50','Blog 열람 50회');
  map.set('blogRead100','Blog 열람 100회');
  map.set('blogScrap1','Blog 스크랩 1회');
  map.set('blogScrap50','Blog 스크랩 50회');
  map.set('blogScrap100','Blog 스크랩 100회');
  map.set('selfDocWrite1','자소서 작성 1회');
  map.set('selfDocWrite50','자소서 작성 50회');
  map.set('selfDocWrite100','자소서 작성 100회');
  map.set('jobRead1','공고 열람 1회');
  map.set('jobRead50','공고 열람 50회');
  map.set('jobRead100','공고 열람 100회');
  map.set('jobScrap1','공고 스크랩 1회');
  map.set('jobScrap50','공고 스크랩 50회');
  map.set('jobScrap100','공고 스크랩 100회');
  map.set('game10',' 게임 승리 10회');
  map.set('game30',' 게임 승리 30회');
  map.set('game50',' 게임 승리 50회');
  map.set('game150',' 게임 승리 150회');
  map.set('game300',' 게임 승리 300회');

  const [acq,setAcq] = useState<ReactElement[]>([]); //react 문 보낼것
  const [unacq,setUnacq] = useState<ReactElement[]>([]); //react 문 보낼것

  // useEffect(() => {
  //   const newElements:ReactElement[] = [];
  //   for (let i = 0; i < acquired.length; i++) {
  //     newElements.push(
  //       <div key={i} className="flex flex-col items-center justify-center h-[16vh] w-[16vh] mx-4">
  //         <div className="flex items-center justify-center">
  //           <Image src={`/Images/badge/${acquired[i]}.png`} width={100} height={100} alt={`${acquired[i]}`}/>
  //         </div>
  //         <div className="flex items-center justify-center text-sm font-bold">{map.get(acquired[i])}</div>
  //       </div>
  //     );
  //   }
  //   setAcq(newElements);
  // }, [acquired,map]);

  useEffect(() => {
    const newElements:ReactElement[] = [];
    for (let i = 0; i < unacquired.length; i++) {
      newElements.push(
        <div key={i} className="flex flex-col items-center justify-center h-[16vh] w-[16vh] mx-4">
          <div className="h-[100px] w-[100px] flex items-center justify-center">
            {/* <Image src={`/images/badge/${unacquired[i]}.png`} width={100} height={100} alt="badge"/> */}
            <Image src="/Images/badge/locked.png" width={50} height={50} alt="badge"/>
          </div>
          <div className="flex items-center justify-center text-sm font-bold">{map.get(unacquired[i])}</div>
        </div>
      );
    }
    setUnacq(newElements);
  }, []);

  return (
    <div>
      <div className="flex items-center justify-start mt-6">
        <div className="h-[5vh] w-[15vw] bg-[#CBFFC2] flex items-center justify-center rounded-lg font-bold">내가 획득한 뱃지</div>
      </div>
      <div className="flex items-center justify-start flex-wrap w-full">
        {/* {acq} */}
        <div className="flex flex-col items-center justify-center h-[16vh] w-[16vh] mx-4">
           <div className="flex items-center justify-center">
             <Image src={`/Images/badge/attend1.png`} width={100} height={100} alt="attend1"/>
           </div>
           <div className="flex items-center justify-center text-sm font-bold"></div>
         </div>
         <div className="flex flex-col items-center justify-center h-[16vh] w-[16vh] mx-4">
           <div className="flex items-center justify-center">
             <Image src={`/Images/badge/jobRead1.png`} width={100} height={100} alt="jobRead1"/>
           </div>
           <div className="flex items-center justify-center text-sm font-bold"></div>
         </div>
         <div className="flex flex-col items-center justify-center h-[16vh] w-[16vh] mx-4">
           <div className="flex items-center justify-center">
             <Image src={`/Images/badge/jobRead50.png`} width={100} height={100} alt="jobRead50"/>
           </div>
           <div className="flex items-center justify-center text-sm font-bold"></div>
         </div>
         <div className="flex flex-col items-center justify-center h-[16vh] w-[16vh] mx-4">
           <div className="flex items-center justify-center">
             <Image src={`/Images/badge/jobScrap1.png`} width={100} height={100} alt="jobScrap1"/>
           </div>
           <div className="flex items-center justify-center text-sm font-bold"></div>
         </div>
      </div>
      <div className="flex items-center justify-start mt-6">
        <div className="h-[5vh] w-[15vw] bg-[#CBFFC2] flex items-center justify-center rounded-lg font-bold">획득 가능한 뱃지</div>
      </div>
      <div className="flex items-center justify-start flex-wrap w-full">
        {unacq}
      </div>
    </div>
  )
}
export default MyBadge;