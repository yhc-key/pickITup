import { useEffect, useState, ReactElement } from "react";
import Image from 'next/image';

function MyBadge() {
  let acquired:string[] = ["attend1","jobRead1","jobRead50","jobScrap1"];
  let unacquired:string [] = ["attend50","attend100","blogRead1","blogRead50","blogRead100",
                              "blogScrap1","blogScrap50","blogScrap100","blogWrite1","blogWrite50","blogWrite100",
                              "game10","game30","game50","game150","game300","jobRead100","jobScrap50","jobScrap100"];
  const map = new Map<String,String>();
  map.set('attend1','출석1일');
  map.set('attend50','출석50일');
  map.set('attend100','출석100일');
  map.set('blogRead1','블로그열람 1회');
  map.set('blogRead50','블로그열람 50회');
  map.set('blogRead100','블로그열람 100회');
  map.set('blogScrap1','블로그스크랩 1회');
  map.set('blogScrap50','블로그스크랩 50회');
  map.set('blogScrap100','블로그스크랩 100회');
  map.set('selfDocWrite1','자기소개서작성 1회');
  map.set('selfDocWrite50','자기소개서작성 50회');
  map.set('selfDocWrite100','자기소개서작성 100회');
  map.set('jobRead1','채용공고 열람 1회');
  map.set('jobRead50','채용공고 열람 50회');
  map.set('jobRead100','채용공고 열람 100회');
  map.set('jobScrap1','채용공고 스크랩 1회');
  map.set('jobScrap50','채용공고 스크랩 50회');
  map.set('jobScrap100','채용공고 스크랩 100회');
  map.set('game10',' 게임 승리 10회');
  map.set('game30',' 게임 승리 30회');
  map.set('game50',' 게임 승리 50회');
  map.set('game150',' 게임 승리 150회');
  map.set('game300',' 게임 승리 300회');
  const [acq, setAcq] = useState<string[]>([]);
  const [unacq, setUnacq] = useState([]);
  useEffect(() => {
    const newElements:ReactElement[] = [];
    for (let i = 0; i < acquired.length; i++) {
      newElements.push(
        <div key={i} className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8">
          <Image src={`/images/${acquired[i]}.png`} width={100} height={100} alt="badge"/>
        </div>
      );
    }
    setAcq(newElements);
  }, [acquired]);

  return (
    <div>
      <div className="flex items-center justify-start mt-6">
        <div className="h-[5vh] w-[15vw] bg-[#CBFFC2] flex items-center justify-center rounded-lg">내가 획득한 뱃지</div>
      </div>
      <div className="flex flex-wrap w-full">
        {acq}
        {/* <div className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8"/>
        <div className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8"/>
        <div className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8"/>
        <div className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8"/>
        <div className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8"/>
        <div className="h-[10vh] w-[10vh] bg-[#cbffc2] rounded-lg mx-11 my-8"/> */}
      </div>
    </div>
  )
}
export default MyBadge;