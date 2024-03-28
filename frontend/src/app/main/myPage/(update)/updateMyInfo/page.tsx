"use client";
import Image from "next/image";
import DaumPostcode from "react-daum-postcode";
import { FaCheck } from "react-icons/fa6";
import { techDataMap } from "@/data/techData";
import { useEffect, useState } from "react";
import useAuthStore, { AuthState } from "@/store/authStore";
import Modal from "@/components/modal2";
import TechSelectMyPage from "@/components/techSelectMyPage";

interface ModalProps {
  Onclose: () => void;
}

export default function MyPage() {
  const nickname: string = useAuthStore((state: AuthState) => state.nickname);
  const github: string = useAuthStore((state: AuthState) => state.github);
  const blog: string = useAuthStore((state: AuthState) => state.blog);
  const address: string = useAuthStore((state: AuthState) => state.address);
  const email: string = useAuthStore((state: AuthState) => state.email);
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
  const [newNickname, setNewNickname] = useState<string>("");
  const [newEmail, setNewEmail] = useState<string>("");
  const [newGithub, setNewGithub] = useState<string>("");
  const [newBlog, setNewBlog] = useState<string>("");
  const [newAddress, setNewAddress] = useState<string>("");
  const [isTechSelectOpen, setIsTechSelectOpen] = useState<boolean>(false);
  const [isOpen, setIsOpen] = useState<boolean>(false);
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
    // 주소변경
    let githubValue = newGithub;
    let blogValue = newBlog;
    let emailValue = newEmail;
    if (newGithub === "") {
      githubValue = github;
    }
    if (newBlog === "") {
      blogValue = blog;
    }
    if (newEmail === "") {
      emailValue = email;
    }
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
    <div className="relative flex flex-col h-full pt-6 pb-20 pl-20 border border-f5gray-500 rounded-2xl">
      <h2 className="text-2xl font-bold mb-4">정보 수정하기</h2>
      <div className="relative flex flex-row items-center">
        <div className="absolute">닉네임 </div>
        <input
          value={newNickname}
          onChange={(e) => {
            setNewNickname(e.target.value);
          }}
          placeholder={nickname}
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>
      <p className="mt-1 ml-20 text-sm text-f5green-400 w-[200px] h-[30px]">
        {nickMessage}
      </p>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">주소 </div>
        <input
          readOnly
          onClick={(e) => setIsOpen(true)}
          value={newAddress}
          onChange={(e) => setNewAddress(e.target.value)}
          placeholder={address}
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
        {isOpen && (
          <Modal open={isOpen} clickSide={clickSide} size="h-[500px] w-[800px]">
            <DaumPostcode onComplete={completeHandler} />
          </Modal>
        )}
      </div>
      <p className="mt-1 ml-20 text-sm text-f5green-400 w-[200px] h-[30px]">
        {addressMessage}
      </p>

      <div className="flex flex-wrap mt-4 items-center min-h-12 gap-2 max-w-[1000px]">
        <span className="font-bold"> 기술 스택 </span>
        <button type="button" onClick={() => setIsTechSelectOpen(true)}>
          기술스택 수정하기
        </button>
        {isTechSelectOpen && <TechSelectMyPage onclose={closeTech} />}
      </div>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">Github </div>
        <input
          value={newGithub}
          onChange={(e) => setNewGithub(e.target.value)}
          placeholder={github}
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">tech blog </div>
        <input
          value={newBlog}
          onChange={(e) => setNewBlog(e.target.value)}
          placeholder={blog}
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
      </div>
      <div className="relative flex flex-row items-center mt-3">
        <div className="absolute">Email </div>
        <input
          value={newEmail}
          onChange={(e) => setNewEmail(e.target.value)}
          placeholder={email}
          className="flex items-center w-1/3 h-8 p-2 ml-20 border rounded-lg bg-f5gray-400 min-w-80"
        />
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
          등록하기
        </button>
      </div>
    </div>
  );
}
