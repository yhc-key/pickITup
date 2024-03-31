"use client";
import { TbHandClick } from "react-icons/tb";
import { PiCursorClickFill } from "react-icons/pi";
import { PiCursorClick } from "react-icons/pi";
import Image from "next/image";
import DaumPostcode from "react-daum-postcode";
import { techDataMap } from "@/data/techData";
import { useEffect, useState } from "react";
import useAuthStore, { AuthState } from "@/store/authStore";
import Modal from "@/components/modal2";
import TechSelectMyPage from "@/components/techSelectMyPage"; 
import SelectProfile from "@/components/selectProfile";

export default function MyPage() {
  const github: string = useAuthStore((state: AuthState) => state.github);
  const blog: string = useAuthStore((state: AuthState) => state.blog);
  const email: string = useAuthStore((state: AuthState) => state.email);
  const address: string = useAuthStore((state: AuthState) => state.address);
  const profile: string = useAuthStore((state: AuthState) => state.profile);
  const setNickName: (newNickname: string) => void = useAuthStore(
    (state: AuthState) => state.setNickname
  );
  const setGithub: (newGithub: string) => void = useAuthStore(
    (state: AuthState) => state.setGithub
  );
  const setBlog: (newBlog: string) => void = useAuthStore(
    (state: AuthState) => state.setBlog
  );
  const setEmail: (newEmail: string) => void = useAuthStore(
    (state: AuthState) => state.setEmail
  );
  const setAddress: (newAddress: string) => void = useAuthStore(
    (state: AuthState) => state.setAddress
  );

  const [nickMessage, setNickMessage] = useState<string>("");
  const [addressMessage, setAddressMessage] = useState<string>("");
  const [gitMessage, setGitMessage] = useState<string>("");
  const [blogMessage, setBlogMessage] = useState<string>("");
  const [emailMessage, setEmailMessage] = useState<string>("");

  const [newNickname, setNewNickname] = useState<string>("");
  const [newEmail, setNewEmail] = useState<string>("");
  const [newGithub, setNewGithub] = useState<string>("");
  const [newBlog, setNewBlog] = useState<string>("");
  const [newAddress, setNewAddress] = useState<string>("");
  const [isTechSelectOpen, setIsTechSelectOpen] = useState<boolean>(false);
  const [isProfileOpen, setIsProfileOpen] = useState<boolean>(false);
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [state, setState] = useState({
    nickname: "yonghwna",
  });
  const completeHandler = (data: any) => {
    // 주소 받기 위한
    setNewAddress(data.address);
    setIsOpen(false);
  };
  const resetHandler = () => {
    //reset 버튼
    setNewNickname("");
    setNewAddress("");
    setNewGithub("");
    setNewBlog("");
    setNewEmail("");
  };
  const closeTech = () => {
    //기술 스택 모달 닫기
    setIsTechSelectOpen(false);
  };
  const clickSide = () => {
    //바깥 선택 모달 닫기
    setIsOpen(false);
  };
  const submitHandler = () => {
    //등록하기 버튼클릭

    const token = sessionStorage.getItem("accessToken");
    if (newNickname !== "") {
      const token = sessionStorage.getItem("accessToken");
      fetch("https://spring.pickitup.online/users/nickname", {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: newNickname,
      })
        .then((res) => res.json())
        .then((res) => {
          setNickName(newNickname);
          sessionStorage.setItem("nickname", newNickname);
          setNickMessage("닉네임 변경이 완료되었습니다.");
          setNewNickname("");
          console.log(res);
        });
    }
    else{
      setNickMessage("");
    }
    if (newAddress !== "") {
      fetch("https://spring.pickitup.online/users/address", {
        method: "PATCH",
        headers: {
          Authorization: "Bearer " + token,
        },
        body: newAddress,
      })
        .then((res) => res.json())
        .then((res) => {
          console.log(res);
          setAddress(newAddress);
          setNewAddress("");
          setAddressMessage("주소 변경이 완료되었습니다.");
        });
    }
    else{
      setAddressMessage("");
    }
    // 주소변경
    let githubValue = newGithub;
    let blogValue = newBlog;
    let emailValue = newEmail;
    if (newGithub === "") {
      githubValue = github;
      setGitMessage("");
    }
    else setGitMessage("깃허브 주소가 변경되었습니다.");
    if (newBlog === "") {
      blogValue = blog;
      setBlogMessage("");
    }
    else setBlogMessage("블로그 주소가 변경되었습니다.")
    if (newEmail === "") {
      emailValue = email;
      setEmailMessage("");
    }
    else setEmailMessage("이메일 주소가 변경되었습니다.");
    fetch("https://spring.pickitup.online/users/me", {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({
        github: githubValue,
        techBlog: blogValue,
        email: emailValue,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        setGithub(githubValue);
        setBlog(blogValue);
        setEmail(emailValue);
        setNewBlog("");
        setNewEmail("");
        setNewGithub("");
      });
  };
  return (
    <div className="relative flex flex-col h-full pt-6 pb-20 pl-20 border border-f5gray-400 rounded-2xl">
      <div className="relative w-44 flex flex-row">
      <Image
        src={`/images/profile/${profile}.png`}
        alt="logo"
        width={150}
        height={150}
        priority={true}
        style={{clipPath: "circle()"}}
        className="m-3 cursor-pointer"
        onClick={()=>setIsProfileOpen(true)}
      />
      <PiCursorClick size="40" className="absolute bottom-0 right-0"/>
      </div>
      <h2 className="mb-4 text-xl font-bold ">기본 정보</h2>
      <div className="flex flex-wrap mt-4 items-center min-h-12 gap-2 max-w-[1000px] my-2">
        <span className="font-bold"> 기술 스택 </span>
        <button type="button" onClick={() => setIsTechSelectOpen(true)}
        className="px-6 py-2 ml-6 text-white rounded-lg bg-f5green-300">
          나의 기술스택 수정하기
        </button>
      </div>
      <div className="relative flex flex-row items-center my-2">
        <div className="absolute">닉네임 </div>
        <input
          value={newNickname}
          onChange={(e) => {
            setNewNickname(e.target.value);
          }}
          placeholder="변경할 닉네임을 입력해주세요."
          className="flex items-center w-1/3 p-2 ml-24 border rounded-lg h-9 border-f5gray-400 min-w-80 focus:outline-none focus:bg-white focus:border-f5green-300 "
        />
        <p className="mt-1 ml-5 text-sm text-f5green-400">
          {nickMessage}
        </p>
      </div>
      <div className="relative flex flex-row items-center my-2">
        <div className="absolute">현재 주소 </div>
        <input
          disabled
          value={address}
          onChange={(e) => {
            setAddress(e.target.value);
          }}
          className="flex items-center w-1/3 h-9 p-2 ml-24 border border-f5gray-400  rounded-lg 
            min-w-80 bg-f5gray-400" 
        />
        <p className="mt-1 ml-5 text-sm text-f5green-400">
          {nickMessage}
        </p>
      </div>
      <div className="relative flex flex-row items-center my-2">
        <div className="absolute">변경 주소 </div>
        <input
          readOnly
          onClick={(e) => setIsOpen(true)}
          value={newAddress}
          onChange={(e) => setNewAddress(e.target.value)}
          placeholder="주소를 선택해주세요."
          className="flex items-center w-1/3 p-2 ml-24 border rounded-lg h-9 border-f5gray-400 min-w-80 focus:outline-none focus:bg-white focus:border-f5green-300 "
        />
        
          <Modal open={isOpen} clickSide={clickSide} size="w-1/2 h-4/6">
            <DaumPostcode onComplete={completeHandler} />
          </Modal>
        
        <p className="mt-1 ml-5 text-sm text-f5green-400">
          {addressMessage}
        </p>
      </div>

      <div className="relative flex flex-row items-center my-2">
        <div className="absolute">Github </div>
        <input
          value={newGithub}
          onChange={(e) => setNewGithub(e.target.value)}
          placeholder="Github 아이디를 입력해주세요."
          className="flex items-center w-1/3 p-2 ml-24 border rounded-lg h-9 border-f5gray-400 min-w-80 focus:outline-none focus:bg-white focus:border-f5green-300 "
        />
        <p className="mt-1 ml-5 text-sm text-f5green-400">
          {gitMessage}
        </p>
      </div>
      <div className="relative flex flex-row items-center my-2">
        <div className="absolute">tech blog </div>
        <input
          value={newBlog}
          onChange={(e) => setNewBlog(e.target.value)}
          placeholder="blog URL을 입력해주세요."
          className="flex items-center w-1/3 p-2 ml-24 border rounded-lg h-9 border-f5gray-400 min-w-80 focus:outline-none focus:bg-white focus:border-f5green-300 "
        />
        <p className="mt-1 ml-5 text-sm text-f5green-400">
          {blogMessage}
        </p>
      </div>
      <div className="relative flex flex-row items-center my-2">
        <div className="absolute">Email </div>
        <input
          value={newEmail}
          onChange={(e) => setNewEmail(e.target.value)}
          placeholder="Email을 입력해주세요."
          className="flex items-center w-1/3 p-2 ml-24 border rounded-lg h-9 border-f5gray-400 min-w-80 focus:outline-none focus:bg-white focus:border-f5green-300 "
        />
        <p className="mt-1 ml-5 text-sm text-f5green-400">
          {emailMessage}
        </p>
      </div>

      <div className="absolute bottom-0 right-0 mb-6 mr-6">
        <button
          onClick={resetHandler}
          type="reset"
          className="px-6 py-2 mx-4 text-white rounded-lg bg-f5red-300"
        >
          초기화
        </button>
        <button
          onClick={submitHandler}
          className="px-6 py-2 mx-4 text-white rounded-lg bg-f5green-300"
        >
          수정하기
        </button>
      </div>
      <TechSelectMyPage open={isTechSelectOpen} onclose={closeTech} />
      <SelectProfile open={isProfileOpen} onclose={()=>setIsProfileOpen(false)} />
    </div>
    
  );
}
