"use client";
import { MdEmail } from "react-icons/md";
import { SiVelog } from "react-icons/si";
import { FaUserEdit } from "react-icons/fa";
import { FaSquareGithub } from "react-icons/fa6";
import Image from "next/image";
import Link from "next/link";
import useAuthStore, { AuthState } from "@/store/authStore";
import { useEffect, useState } from "react";
import ExperienceBar from "@/components/experienceBar";
const dummyMyData: string[][] = [
  ["내가 찜한 채용공고", "3 개", "/images/starOutline.png"],
  ["마감 임박 채용공고", "1 개", "/images/history.png"],
  ["문제 풀이 수", "64", "/images/iconLibraryBooks.png"],
  ["내 뱃지", "3 개", "/images/iconShield.png"],
];

export default function MyPageLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const nickname: string = useAuthStore((state: AuthState) => state.nickname);
  const github: string = useAuthStore((state: AuthState) => state.github);
  const blog: string = useAuthStore((state: AuthState) => state.blog);
  const email: string = useAuthStore((state: AuthState) => state.email);
  const address: string = useAuthStore((state: AuthState) => state.address);
  const profile: string = useAuthStore((state: AuthState) => state.profile);
  const setGithub: (newGithub: string)=> void = useAuthStore((state: AuthState) => state.setGithub);
  const setBlog: (newGithub: string)=> void = useAuthStore((state: AuthState) => state.setBlog);
  const setEmail: (newGithub: string)=> void = useAuthStore((state: AuthState) => state.setEmail);
  const setAddress: (newAddress: string)=> void = useAuthStore((state: AuthState) => state.setAddress);


  const [scrapCount, setScrapCount] = useState<number>(0);
  const [closeCount, setCloseCount] = useState<number>(0);
  const [solvedCount, setSolvedCount] = useState<number>(0);
  const [attendCount, setAttendCount] = useState<number>(0);
  const [badgeCount, setBadgeCount] = useState<number>(0);
  const [level, setLevel] = useState<number>(0);
  const [prev, setPrev] = useState<number>(0);
  const [next, setNext] = useState<number>(0);
  const [exp, setExp] = useState<number>(0);
  useEffect(() => {
    const token = sessionStorage.getItem('accessToken');
    if (token !== null) {
      fetch("https://spring.pickitup.online/users/me", {
        method: "GET",
        headers: {
          "Authorization": "Bearer " + token
        }
      })
      .then(res => res.json())
      .then(res => {
        console.log(res);
        if(res.success===true){
          setScrapCount(res.response.totalMyScrap);
          setCloseCount(res.response.closingScrap);
          setSolvedCount(res.response.solvedInterviewCount);
          setAttendCount(res.response.attendCount);
          setBadgeCount(res.response.totalMyBadge);
          setEmail(res.response.email);
          setLevel(res.response.level);
          setPrev(res.response.prevExp);
          setNext(res.response.nextExp);
          setExp(res.response.exp);
          if(res.response.github === null) setGithub("정보 없음");
          else setGithub(res.response.github);
          if(res.response.techBlog === null) setBlog("정보 없음");
          else setBlog(res.response.techBlog);
          if(res.response.address === null) setAddress("정보 없음");
          else setAddress(res.response.address);
        }
      })
    }
  }, [])
  return (
    <div className="flex mx-32 my-5">
      <div className="min-w-[330px] max-w-[330px]">
        <div className="flex flex-row justify-center gap-10">
          <Image
            src={`/images/profile/${profile}.png`}
            alt="profile1"
            width="100"
            height="100"
            className="rounded-full"
            style={{clipPath:'circle()'}}
          />
          <div className="flex flex-col items-center justify-center gap-5">
            <p>{nickname}</p>
            <Link href="/main/myPage/updateMyInfo" className="flex flex-row justify-center items-center gap-2">
            <FaUserEdit size="25" color="#00ce7c"/>
              내 정보 수정
            </Link>
          </div>
        </div>
        <div className="flex flex-row gap-4 my-4 items-center justify-center">
          <p className="mr-4">Level {level}</p>
          <ExperienceBar prev={prev} next={next} exp={exp} />
        </div>
        <div className="p-4 my-4 border rounded-lg border-f5gray-400">
          <div className="flex justify-between">
            <div className="flex flex-row items-center gap-1">
              <Image
                src="/images/starOutline.png"
                width="15"
                height="15"
                alt="icon"
              />
              <p className="text-sm">내가 찜한 채용공고</p>
            </div>
            <p className="text-sm">{scrapCount} 개</p>
          </div>
          <div className="flex justify-between mt-3">
            <div className="flex flex-row items-center gap-1">
              <Image
                src="/images/history.png"
                width="15"
                height="15"
                alt="icon"
              />
              <p className="text-sm">마감 임박 채용공고</p>
            </div>
            <p className="text-sm">{closeCount} 개</p>
          </div>
          <div className="flex justify-between mt-3">
            <div className="flex flex-row items-center gap-1">
              <Image
                src="/images/iconLibraryBooks.png"
                width="15"
                height="15"
                alt="icon"
              />
              <p className="text-sm">문제 풀이 수</p>
            </div>
            <p className="text-sm">{solvedCount} 개</p>
          </div>
          <div className="flex justify-between mt-3">
            <div className="flex flex-row items-center gap-1">
              <Image
                src="/images/iconShield.png"
                width="15"
                height="15"
                alt="icon"
              />
              <p className="text-sm">내 뱃지</p>
            </div>
            <p className="text-sm">{badgeCount} 개</p>
          </div>
        </div>
        <div className="p-3 border rounded-lg border-f5gray-400">
          <p className="mb-2 font-bold">내 기술 스택</p>
          <div className="flex flex-row flex-wrap gap-2">
            <div className="flex items-center justify-center text-center border rounded-lg border-f5gray-400 p-auto min-w-16 h-7">
              프론트
            </div>
            <Image
              src="/images/techLogoEx/Angular.png"
              alt="앵귤러"
              width="80"
              height="7"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/ReactJS.png"
              alt="리액트"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/ReactNative.png"
              alt="리액트네이티브"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/VueJS.png"
              alt="뷰"
              width="80"
              height="10"
              className="w-auto h-7"
            />
          </div>
          <div className="flex flex-row flex-wrap gap-2 mt-4">
            <div className="flex items-center justify-center text-center border rounded-lg border-f5gray-400 p-auto min-w-16 h-7">
              백앤드
            </div>
            <Image
              src="/images/techLogoEx/Java.png"
              alt="자바"
              width="80"
              height="7"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/Spring.png"
              alt="스프링"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/SpringBoot.png"
              alt="스프링부트"
              width="80"
              height="10"
              className="w-auto h-7"
            />
          </div>
          <div className="flex flex-row flex-wrap gap-2 mt-4">
            <div className="flex items-center justify-center w-20 text-center border rounded-lg border-f5gray-400 p-auto h-7">
              DevOps
            </div>
            <Image
              src="/images/techLogoEx/Github.png"
              alt="깃허브"
              width="80"
              height="7"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/Docker.png"
              alt="도커"
              width="80"
              height="10"
              className="w-auto h-7"
            />
            <Image
              src="/images/techLogoEx/Kubernetes.png"
              alt="젠킨스"
              width="80"
              height="10"
              className="w-auto h-7"
            />
          </div>
        </div>
        <div className="p-3 mt-4 text-sm border rounded-lg border-f5gray-400">
          <div className="flex flex-row items-center gap-3">
            <FaSquareGithub size="25" />{" "}
            <a href={`https://github.com/${github}`}>{github}</a>
          </div>
          <div className="flex flex-row items-center gap-3 mt-2">
            <SiVelog size="22" className="ml-0.5" />{" "}
            <a href={blog}>{blog}</a>
          </div>
          <div className="flex flex-row items-center gap-3 mt-2">
            <MdEmail size="25" className="ml-0.5" />{" "}
            {email}
          </div>
        </div>
      </div>
      <div className="flex-grow ml-16">{children}</div>
    </div>
  );
}
