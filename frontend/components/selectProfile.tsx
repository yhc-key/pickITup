import { useEffect, useState } from "react";
import Modal from "./modal2";
import Image from "next/image";
import useAuthStore,{AuthState} from "@/store/authStore";
const profiles:number[] = [1,2,3,4];
interface SelectProfileProps{
  open:boolean;
  onclose:()=>void;
}
export default function SelectProfile({open,onclose}:SelectProfileProps){
  const [isOpen,setIsOpen] = useState<boolean>(false);
  const setProfile: (newProfile: number) => void = useAuthStore(
    (state: AuthState) => state.setProfile
  );
  useEffect(()=>{
    setIsOpen(open);
  },[open])
  const changeProfileHandler = (item:number) => {
    if(item!==null){
      const token = sessionStorage.getItem('accessToken');
      fetch("https://spring.pickitup.online/users/profile/image",{
        method: "PATCH",
        headers: {
          Authorization: "Bearer "+token,
          "Content-Type": "text/plain",
        },
        body: String(item)
      })
      .then(res=>res.json())
      .then(res=>console.log(res))
    }
    setProfile(item);
    setIsOpen(false);
    onclose();
  }
  return (
    <div>
      <Modal open={isOpen} clickSide={()=>{setIsOpen(false);onclose();}} size="w-3/6 h-5/6">
        <div className="flex flex-col items-center justify-center">
          <h1 className="text-lg">변경할 프로필 사진을 클릭하세요!</h1>
          <div className="flex flex-wrap justify-center items-center gap-10 mt-10">
            {profiles.map((item:number,index:number)=>(
              <Image
                key={index}
                src={`/images/profile/profile${item}.png`}
                width="150"
                height="150"
                alt={`${item}`}
                style={{clipPath: "circle()"}}
                className="m-3 cursor-pointer"
                onClick={()=>{changeProfileHandler(item)}}
              />
            ))}
          </div>
        </div>
      </Modal>
    </div>
  )
}