"use client";
import { useState, useEffect} from "react";
import Image from "next/image";
import Link from "next/link";
import { TiDeleteOutline } from "react-icons/ti";
import Modal from "@/components/modal2";;
import { techDataMap } from "@/data/techData";
import { techData2,techInfos,techAll } from "@/data/techData";
import AllSearchBar from "@/components/AllSearchBar"
interface TechSelectMyPageProps{
  onclose: ()=>void;
  open : boolean;
}
export default function TechSelectMyPage({onclose,open}:TechSelectMyPageProps) {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [pickTech, setPickTech] = useState<string[]>([]);
  useEffect(()=>{
    if(open){
      setIsModalOpen(true);
    }
    else setIsModalOpen(false);
  },[])
  useEffect(()=>{
    if(open){
      setIsModalOpen(true);
    }
    else setIsModalOpen(false);
  },[open])
  useEffect(() => {
    const authid = sessionStorage.getItem('authid');
    const token = sessionStorage.getItem('accessToken');
    if(authid !== null) {
      fetch("https://spring.pickitup.online/users/keywords",{
        method : "GET",
        headers:{
          "Authorization": "Bearer "+token
        }
      })
      .then(res=>res.json())
      .then(res=>{
          console.log(res);
          setPickTech(res.response.keywords);
          if(open){
            setIsModalOpen(true);
          }
          else{setIsModalOpen(false);}


        }
      )
    }
  }, [open]);

  const clickSide = () => {
    modalCloseHandler();

  }
  // 기술스택 추가 함수
  const techAddHandler = (tech: string): void => {
    if(!pickTech.includes(tech)){
      setPickTech([...pickTech,tech]);
    }
  };
  //기술스택 제거 함수
const deletePickTech = (item : string)=>{
  const newTechs = pickTech.filter(tech=>tech!== item);
  setPickTech(newTechs);
}
  // 모달 닫는 함수
  const modalCloseHandler = (): void => {
    // setPickTech([]);
    setIsModalOpen(false);
    onclose();
  };

  const setMyTech = () :void => {
    const authid = sessionStorage.getItem('authid');
    const token = sessionStorage.getItem('accessToken');
    if(authid===null)return;
    fetch("https://spring.pickitup.online/users/keywords",{
      method : "GET",
      headers:{
        "Authorization": "Bearer "+token,
      }
    })
    .then(res=>res.json())
    .then(res=>{
      const techIds:number[] = [];
        for (const tech of pickTech) {
          const techId = techInfos.get(tech);
          if (techId !== undefined) {
              techIds.push(techId);
          }
        }
        
        fetch(`https://spring.pickitup.online/users/keywords`,{
          method : "PATCH",
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
      {/* { isModalOpen && */}
      <Modal open={isModalOpen} clickSide={clickSide} size="h-5/6 w-7/12">
        <div className="flex flex-col items-center h-6/12">
          <div className="mb-5 text-xl font-medium text-center">
            관심 기술 스택(영어)을 선택해주세요
          </div>
          <div className="z-50 py-2 flex items-center justify-center"> 
            <AllSearchBar words={techAll} onSelect={techAddHandler}></AllSearchBar>
          </div>
          
          <div className="flex flex-wrap items-center justify-center mb-1 text-sm text-center z-40 min-h-12">
            {pickTech.map((item:string,index:number)=>
              <div key={index}  
              className="flex items-center justify-center py-1 pr-2 relative border-2 border-f5green-300 rounded-2xl text-xs p-2 mx-2 my-1 min-h-5">
                {techData2.includes(item)?
                <Image
                src={`/images/ITUlogo.png`}
                alt={item}
                width={18}
                height={18}
                className="inline-block mr-1 "/>
                :
                <Image
                src={`/images/techLogo/${item}.png`}
                alt={item}
                width={20}
                height={20}
                className="inline-block mr-1"
                />
                }{item}
              <div className="ml-1 cursor-pointer"  onClick={()=>deletePickTech(item)}><TiDeleteOutline color="red" size="20"/> </div></div>
            )}
          </div>
          {/* <div className="flex flex-wrap justify-center gap-2 mt-1">
          </div> */}
          <div className="fixed bottom-4 flex justify-center items-center">
            <button
              onClick={modalCloseHandler}
              className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10"
            >
              나중에 하기
            </button>
            <div className="w-8"></div>
            <Link href={`/main/myPage/updateMyInfo`} onClick={()=>{setMyTech(),modalCloseHandler()}}>
              <button className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10">
                저장하기
              </button>
            </Link>
          </div>
        </div>
      </Modal>
      {/* }  */}
    </div>
    
  );
}