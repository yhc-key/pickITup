"use client";
import { useState, useEffect} from "react";
import Image from "next/image";
import Link from "next/link";
import { TiDeleteOutline } from "react-icons/ti";
import Modal from "@/components/modal";;
import { techDataMap } from "@/data/techData";
import { techData2,techInfos } from "@/data/techData2";
import AutocompleteSearchBar from "@/components/AutoCompleteSearchBar";

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


export default function TechSelectAfterLogin() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [pickType, setPickType] = useState("언어");
  const [pickTech, setPickTech] = useState<string[]>([]);
  
  useEffect(() => {
    const authid = sessionStorage.getItem('authid');
    if(authid !== null) {
      fetch(`https://spring.pickitup.online/users/${authid}/keywords`,{
        method : "GET"
      })
      .then(res=>res.json())
      .then(res=>{
        if(res.response.keywords.length==0){
          setIsModalOpen(true); // 첫 로그인 후 모달 열기
        }
      })
    }
  }, []);
  // 기술스택 선택 함수
  const techAddHandler = (tech: string): void => {
    if(!pickTech.includes(tech)){
      setPickTech([...pickTech,tech]);
    }
  };
const deletePickTech = (item : string)=>{
  const newTechs = pickTech.filter(tech=>tech!== item);
  setPickTech(newTechs);
}
  // 모달 닫는 함수
  const modalCloseHandler = (): void => {
    setPickType("언어");
    setPickTech([]);
    setIsModalOpen(false);
  };
  
  const techs: string[] | undefined = techDataMap.get(pickType);

  const setMyTech = () => {
    const authid = sessionStorage.getItem('authid');
    if(authid===null)return;
    fetch(`https://spring.pickitup.online/users/${authid}/keywords`,{
      method : "GET"
    })
    .then(res=>res.json())
    .then(res=>{
      const techIds:number[] = [];
        for (const tech of pickTech) {
          const techId = techInfos.get(tech);
          if (techId !== undefined&&!res.response.keywords.includes(tech)) {
              techIds.push(techId);
          }
        }
        const token = sessionStorage.getItem('accessToken');
        fetch(`https://spring.pickitup.online/users/keywords`,{
          method : "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer "+token,
          },
          body: JSON.stringify({
            keywords : techIds
          })
        })
        .then(res=>res.json())
        .then(res=>console.log(res));
      }
    )
  }

  return (
    <div>
      {/* <button onClick={(): void => setIsModalOpen(true)}>
      </button> */}
      <Modal open={isModalOpen}>
        <div className="flex flex-col items-center">
          <div className="mb-5 text-xl font-medium text-center">
            관심 기술 스택(영어)을 선택해주세요
          </div>
          <div className="z-50 py-2 h-[8vh] flex items-center justify-center"> 
            <AutocompleteSearchBar words={techData2} onSelect={techAddHandler} ></AutocompleteSearchBar>
          </div>

          <div className="flex flex-wrap items-center justify-center mb-1 text-sm text-center z-40 min-h-[14vh]">
            {pickTech.map((item:string,index:number)=>
              <button key={index} onClick={()=>deletePickTech(item)} className="relative border-2 border-f5green-300 rounded-2xl text-xs p-2 mx-2 my-1">{item}
              <div className="absolute -top-2 -right-2"><TiDeleteOutline color="red" /> </div></button>
            )}
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
          <div className="m-4 border-t-2"></div>
          <div className="min-h-[250px]">
            <div className="flex flex-wrap justify-center gap-4">
              {techs?.map((tech: string, index: number) => {
                const isActive: boolean = pickTech.includes(tech);
                return (
                  <button
                    type="button"
                    key={index}
                    onClick={() => {!isActive?techAddHandler(tech):deletePickTech(tech)}}
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
          <div className="fixed bottom-9 flex justify-center items-center">
            <Link href={`/main/myPage/updateMyInfo`} onClick={setMyTech}>
              <button className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10">
                등록하기
              </button>
            </Link>
          </div>
        </div>
      </Modal>
    </div>
  );
}
