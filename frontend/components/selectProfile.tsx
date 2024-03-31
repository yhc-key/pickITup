import { useEffect, useState } from "react";
import Modal from "./modal2";
import Image from "next/image";
import useAuthStore,{AuthState} from "@/store/authStore";
const profile:string[] = ["profile1","profile2","profile3","profile4"];
interface SelectProfileProps{
  open:boolean;
  onclose:()=>void;
}
export default function SelectProfile({open,onclose}:SelectProfileProps){
  const [isOpen,setIsOpen] = useState<boolean>(false);
  const setProfile: (newProfile: string) => void = useAuthStore(
    (state: AuthState) => state.setProfile
  );
  useEffect(()=>{
    setIsOpen(open);
  },[open])
  return (
    <div>
      <Modal open={isOpen} clickSide={()=>{setIsOpen(false);onclose();}} size="w-3/6 h-5/6">
        <div className="flex flex-col items-center justify-center">
          <h1 className="text-lg">변경할 프로필 사진을 클릭하세요!</h1>
          <div className="flex flex-wrap justify-center items-center gap-10 mt-10">
            {profile.map((item,index)=>(
              <Image
                key={index}
                src={`/images/profile/${item}.png`}
                width="150"
                height="150"
                alt={item}
                style={{clipPath: "circle()"}}
                className="m-3 cursor-pointer"
                onClick={()=>{setProfile(item);setIsOpen(false);onclose();}}
              />
            ))}
          </div>
        </div>
      </Modal>
    </div>
  )
}