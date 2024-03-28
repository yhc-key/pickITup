
"use client";
import DaumPostcode from "react-daum-postcode";
import Modal from "@/components/modal2";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

export default function Signup() {
  const router = useRouter();
  const [availableId, setAvailableId] = useState<string>("");
  const [id, setId] = useState<string>("");

  const [isValidPassword, setIsValidPassword] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const [samepass, setSamepass] = useState<string>("");
  const [issame, setIssame] = useState<boolean>(false);

  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [isOpen, setIsOpen] = useState<boolean>(false); 
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  const clickSide = () =>{
    setIsModalOpen(false);
    setIsOpen(false);
  }
  useEffect(() => {
    if (samepass === password) {
      setIssame(true);
    } else {
      setIssame(false);
    }
  }, [samepass, password]);

  useEffect(() => {
    // 비밀번호 유효성 검사
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    if (regex.test(password)) {
      setIsValidPassword("true");
    } else {
      setIsValidPassword("false");
    }
  }, [password]);

  const completeHandler = (data:any) =>{
    setAddress(data.address);
    setIsOpen(false); //추가
    setIsModalOpen(false);
  }

  const checkid = () => {
    if (id === "") {
      setAvailableId("false");
      return;
    }
    fetch("https://spring.pickitup.online/auth/check/" + id, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((res) => {
        if (res.success === true) {
          setAvailableId("true");
        } else if (res.success === false) {
          setAvailableId("false");
        }
      });
  };

  const signUpRequest = () => {
    if (availableId !== "true") {
      alert("중복확인 후 올바른 아이디를 다시 입력해주세요");
      return;
    }
    if (!isValidPassword) {
      alert("올바른 비밀번호를 다시 입력해주세요");
      return;
    }
    if (!issame) {
      alert("동일한 비밀번호를 다시 입력해주세요");
      return;
    }
    if (name === "") {
      alert("이름을 작성해주세요");
      return;
    }
    if(address === ""){
      alert("주소를 검색 후 선택해주세요");
      return;
    }
    fetch("https://spring.pickitup.online/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: id,
        password: password,
        name: name,
        nickname: name,
        email: email,
        address: address,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
        router.push("/main/login");
      });
  };

  return (
    <div className="flex flex-col justify-center items-center w-full">
      <div className="flex items-center justify-center h-[10vh] text-xl font-bold">
        pick IT up 회원가입
      </div>
      <div className="w-[48vw] h-[62vh] rounded-[10px] border border-f5gray-400">
        <div>
          <div className="flex w-full h-[6vh] justify-center items-center mt-4">
            <label htmlFor="id" className="w-[10vw] text-lg font-black">
              아이디
            </label>
            <input
              value={id}
              onChange={(e) => {setId(e.target.value);setAvailableId("check");}}
              className="w-[16vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none pl-2
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
            <div className="w-[1vw]"></div>
            <button
              onClick={(e) => {
                checkid(), e.preventDefault();
              }}
              className="w-[7vw] h-[4vh] flex items-center justify-center rounded-md bg-f5green-300 text-xs text-white font-bold"
            >
              중복확인
            </button>
          </div>
          <div className="flex w-full h-[1vh] justify-center items-center mb-2">
            <div className="w-[10vw]"></div>
            {availableId === "true" ? (
              <div className="w-[24vw] text-xs text-[#5A85C5]">
                사용 가능한 아이디 입니다!
              </div>
            ) : (
              <></>
            )}
            {availableId === "false" ? (
              <div className="w-[24vw] text-xs text-[#C55A5A]">
                사용할 수 없는 아이디입니다!
              </div>
            ) : (
              <></>
            )}
            {availableId === "check" ? (
              <div className="w-[24vw] text-xs text-[#C55A5A]">
                중복확인을 해주시길 바랍니다!
              </div>
            ) : (
              <></>
            )}
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label htmlFor="password" className="w-[10vw] text-lg font-black">
              비밀번호
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
              }}
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none pl-2
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
          </div>
          <div className="flex w-full h-[1vh] justify-center items-center mb-2">
            <div className="w-[10vw]"></div>
            <div className="w-[24vw] text-xs text-[#C55A5A]">
              {isValidPassword === "false"
                ? "영문자, 숫자를 포함하여 8자 이상을 입력해주세요"
                : ""}
            </div>
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label htmlFor="password" className="w-[10vw] text-lg font-black">
              비밀번호 확인
            </label>
            <input
              type="password"
              value={samepass}
              onChange={(e) => setSamepass(e.target.value)}
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none pl-2
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
          </div>
          <div className="flex w-full h-[1vh] justify-center items-center mb-2">
            <div className="w-[10vw]"></div>
            <div className="w-[24vw] text-xs text-[#C55A5A]">
              {issame ? "" : "위의 비밀번호와 동일하게 입력해주세요"}
            </div>
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <div className="w-[10vw] text-lg font-black">이름</div>
            <input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none pl-2
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
          </div>

          <div className="w-full h-[1vh] mb-2" />

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label className="w-[10vw] text-lg font-black">이메일</label>
            <input
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
              }}
              placeholder="example@naver.com"
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none pl-2
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300"
            />
          </div>
          <div className="flex w-full h-[6vh] justify-center items-center">
            <label htmlFor="password" className="w-[10vw] text-lg font-black">
              주소
            </label>
            <input
              value={address}
              onChange={(e)=>{setAddress(e.target.value)}}
              placeholder="주소"
              disabled
              className="
              w-[20vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none pl-2 readOnly text-xs
              text-gray-700 leading-tight focus:outline-none focus:bg-white"
            />
            <div className="w-[1vw]"></div>
            <button
              onClick={(e) => {setIsModalOpen(true),setIsOpen(true);}}
              className="w-[3vw] h-[4vh] flex items-center justify-center rounded-md
               bg-f5green-300 text-xs text-white font-bold"
            >검색</button>
            {isOpen && <Modal open={isModalOpen} clickSide={clickSide} size="h-[500px] w-[800px]" >
              <DaumPostcode onComplete={completeHandler}/>
            </Modal>}
          </div>
          <div className="flex w-full h-[14vh] justify-center items-center">
            <button
              onClick={signUpRequest}
              className="w-[24vw] h-[6vh] rounded-md bg-f5green-300 text-white font-bold">
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
