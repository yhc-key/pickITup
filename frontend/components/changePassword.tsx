import Swal from "sweetalert2";
import Modal from "./modal2";
import { useState,useEffect} from "react";
interface ChangePasswordProps{
  onclose:() => void;
  open: boolean;
}
export default function ChangePassword({onclose,open}:ChangePasswordProps){
  const Swal = require('sweetalert2');
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [password, setPassword] = useState<string>("");
  const [lastPassword, setLastPassword] = useState<string>("");
  const [samepass, setSamepass] = useState<string>("");
  const [isValidPassword, setIsValidPassword] = useState<boolean>(false);
  const [isSame, setIsSame] = useState<boolean>(false);
  useEffect(() => {
    if(open) setIsOpen(true);
    else setIsOpen(false);
  },[open])
  useEffect(() => {
    // 비밀번호 유효성 검사
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    if (regex.test(password)) {
      setIsValidPassword(true);
    } else {
      setIsValidPassword(false);
    }
  }, [password]);
  useEffect(() => {
    if (samepass === password) {
      setIsSame(true);
    } else {
      setIsSame(false);
    }
  }, [samepass, password]);

  const changePassRequest = () => {
    // e.preventDefault();
    
    const token = sessionStorage.getItem('accessToken');
    fetch("https://spring.pickitup.online/auth/password",{
      method: "POST",
      headers: {
        "Authorization": "Bearer "+token,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        password: lastPassword
      })
    })
    .then(res=>res.json())
    .then(res=>{
      console.log(res);
      if(res.success===false){
        Swal.fire({
          title: 'Password Error!',
          text: '기존 비밀번호를 올바르게 입력해주세요.',
          icon: 'warning',
          confirmButtonText: '확인',
          confirmButtonColor: '#00ce7c'
        })
        return;
      }
      else if(res.success===true){
        if(!isValidPassword){
          Swal.fire({
            title: 'New Password Error!',
            text: '조건에 맞게 새로운 비밀번호를 입력해주세요.',
            icon: 'warning',
            confirmButtonText: '확인',
            confirmButtonColor: '#00ce7c'
          })
          return;
        }
        if(!isSame){
          Swal.fire({
            title: 'Password Check Error!',
            text: '동일한 비밀번호를 다시 입력해주세요.',
            icon: 'warning',
            confirmButtonText: '확인',
            confirmButtonColor: '#00ce7c'
          })
          return;
        }
        fetch("https://spring.pickitup.online/auth/password",{
          method: "PUT",
          headers: {
            "Authorization": "Bearer "+token,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            password: password
          })
        })
        .then(() => {
          Swal.fire({
            title: 'Success!',
            text: '비밀번호 변경이 완료 되었습니다!',
            icon: 'success',
            timer: 700,
            confirmButtonText: '확인',
            confirmButtonColor: '#00ce7c'
          })
          setIsOpen(false);
          onclose();
          setIsSame(false);
          setIsValidPassword(false);
          setPassword("");
          setLastPassword("");
          setSamepass("");
        })
      }
    })
  }
  return (
    <div>
      <Modal open={isOpen} clickSide={() => {setIsOpen(false);onclose();}} size = "" >
        <div>
          <div className="flex flex-col my-6">
            <div className="flex items-center justify-center">
              <label className="w-32 ">
                기존 비밀번호
              </label>
              <input
                type="password"
                value={lastPassword}
                onChange={(e) => {
                  setLastPassword(e.target.value);
                }}
                placeholder="기존 비밀번호를 입력해주세요."
                className="px-3 py-2 text-sm border rounded-md appearance-none 
                w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400
                 text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
              />
            </div>
          </div>
          <div className="flex items-center justify-center">
            <label className="w-32 ">
                  새 비밀번호
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => {setPassword(e.target.value);}}
              placeholder="비밀번호를 입력해주세요."
              className="px-3 py-2 text-sm border rounded-md appearance-none 
              w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400
               text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
            />
          </div>
          <div className="min-h-5 flex items-center justify-start pt-1 text-xs pl-[8.5rem]">
            <div className="text-[#C55A5A]">
              {isValidPassword === false
                ? "영문자, 숫자를 포함하여 8자 이상을 입력해주세요"
                : ""}
            </div>
          </div>
        </div>
        <div className="flex flex-col">
          <div className="flex items-center justify-center h-10">
            <label htmlFor="password" className="w-32 ">
              비밀번호 확인
            </label>
            <input
              type="password"
              value={samepass}
              onChange={(e) => setSamepass(e.target.value)}
              placeholder="비밀번호를 다시 한 번 입력해주세요."
              className="px-3 py-2 text-sm border rounded-md appearance-none 
              w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400 mt-4
               text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
            />
          </div>
          <div className="min-h-5 flex items-center justify-start pt-1 text-xs pl-[8.5rem] mt-2">
            <div className=" text-[#C55A5A] ">
              {isSame ? "" : "비밀번호가 서로 일치하지 않습니다."}
            </div>
          </div>
          <div className="flex items-center justify-center w-full mt-10">
            <div
              onClick={changePassRequest}
              className="flex items-center justify-center w-1/2 h-12 font-semibold text-white rounded-md bg-f5green-300 cursor-pointer">
              변경하기
            </div>
          </div>
        </div>
      </Modal>
    </div>
  )
}