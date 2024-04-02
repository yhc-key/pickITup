"use client";
import TechSelectMyPage from "@/components/techSelectMyPage";
import { techData2 } from "@/data/techData";
import { MdEmail } from "react-icons/md";
import { SiVelog } from "react-icons/si";
import { FaUserEdit } from "react-icons/fa";
import { FaSquareGithub } from "react-icons/fa6";
import Image from "next/image";
import Link from "next/link";
import useAuthStore, { AuthState } from "@/store/authStore";
import { useEffect, useState } from "react";
import ExperienceBar from "@/components/experienceBar";
import { useMediaQuery } from "react-responsive";
import { usePathname } from "next/navigation";
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
  const profile: number = useAuthStore((state: AuthState) => state.profile);
  const keywords: string[] = useAuthStore((state: AuthState) => state.keywords);
  const setGithub: (newGithub: string) => void = useAuthStore(
    (state: AuthState) => state.setGithub
  );
  const setBlog: (newGithub: string) => void = useAuthStore(
    (state: AuthState) => state.setBlog
  );
  const setEmail: (newGithub: string) => void = useAuthStore(
    (state: AuthState) => state.setEmail
  );
  const setAddress: (newAddress: string) => void = useAuthStore(
    (state: AuthState) => state.setAddress
  );
  const setKeywords: (newKeywords: string[]) => void = useAuthStore(
    (state: AuthState) => state.setKeywords
  );
  const setProfile: (newProfile: number) => void = useAuthStore(
    (state: AuthState) => state.setProfile
  );

  const [isTechSelectOpen, setIsTechSelectOpen] = useState<boolean>(false);
  const [hiddenLayout, setHiddenLayout] = useState(false);
  const [scrapCount, setScrapCount] = useState<number>(0);
  const [closeCount, setCloseCount] = useState<number>(0);
  const [solvedCount, setSolvedCount] = useState<number>(0);
  const [attendCount, setAttendCount] = useState<number>(0);
  const [badgeCount, setBadgeCount] = useState<number>(0);
  const [level, setLevel] = useState<number>(0);
  const [prev, setPrev] = useState<number>(0);
  const [next, setNext] = useState<number>(0);
  const [exp, setExp] = useState<number>(0);

  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });
  const pathname = usePathname();

  useEffect(() => {
    const myKeywords: string | null = sessionStorage.getItem("keywords");
    if (myKeywords !== null) {
      setKeywords(JSON.parse(myKeywords));
    }
    const token = sessionStorage.getItem("accessToken");
    if (token !== null) {
      fetch("https://spring.pickitup.online/users/me", {
        method: "GET",
        headers: {
          Authorization: "Bearer " + token,
        },
      })
        .then((res) => res.json())
        .then((res) => {
          console.log(res);
          if (res.success === true) {
            setScrapCount(res.response.totalMyScrap);
            setCloseCount(res.response.closingScrap);
            setSolvedCount(res.response.solvedInterviewCount);
            setAttendCount(res.response.attendCount);
            setBadgeCount(res.response.totalMyBadge);
            setProfile(res.response.profile);
            setEmail(res.response.email);
            setLevel(res.response.level);
            setPrev(res.response.prevExp);
            setNext(res.response.nextExp);
            setExp(res.response.exp);
            if (res.response.github === null) setGithub("정보 없음");
            else setGithub(res.response.github);
            if (res.response.techBlog === null) setBlog("정보 없음");
            else setBlog(res.response.techBlog);
            if (res.response.address === null) setAddress("정보 없음");
            else setAddress(res.response.address);
          }
        });
    }
  }, []);

  useEffect(() => {
    setHiddenLayout(pathname === "/main/myPage/addEssay");
  }, [pathname]);
  return (
    <div className={`flex my-5 ${isMobile ? "flex-col" : "mx-32 "}`}>
      <div
        className={`min-w-[330px] max-w-[330px]  mx-auto ${hiddenLayout ? "hidden" : ""}`}
      >
        <div className="flex flex-row justify-center gap-10">
          <Image
            src={`/images/profile/profile${profile}.png`}
            alt={`profile`}
            width="100"
            height="100"
            className="rounded-full"
            style={{ clipPath: "circle()" }}
          />
          <div className="flex flex-col items-center justify-center gap-5">
            <p>{nickname}</p>
            <Link
              href="/main/myPage/updateMyInfo"
              className="flex flex-row justify-center items-center gap-2"
            >
              <FaUserEdit size="25" color="#00ce7c" />내 정보 수정
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
        <div className="p-3 min-h-72 border rounded-lg border-f5gray-400">
          <div className="flex justify-between">
            <div className="font-bold">내 기술 스택</div>
            <div
              onClick={() => setIsTechSelectOpen(true)}
              className="px-3 h-7 w-15 text-black rounded-lg bg-slate-300 cursor-pointer"
            >
              수정
            </div>
          </div>
          <div className="flex flex-row flex-wrap gap-2 mt-2">
            {keywords.slice(0, 20).map((item: string, index: number) => {
              const skillImage = item.replace(/\s/g, "");
              return (
                <div
                  key={index}
                  className={`flex flex-row border-f5gray-300 border py-1 pr-2 rounded-2xl text-f5black-400 text-xs items-center  hover:transition-all hover:scale-105 hover:ease-in `}
                >
                  {techData2.includes(item) ? (
                    <Image
                      src={`/images/ITUlogo.png`}
                      alt={item}
                      width={20}
                      height={20}
                      className="inline-block mx-1 "
                    />
                  ) : (
                    <Image
                      src={`/images/techLogo/${skillImage}.png`}
                      alt={item}
                      width={20}
                      height={20}
                      className="inline-block mx-1"
                    />
                  )}
                  {item}
                </div>
              );
            })}
          </div>
        </div>
        <div className="p-3 mt-4 text-sm border rounded-lg border-f5gray-400">
          <div className="flex flex-row items-center gap-3">
            <FaSquareGithub size="25" />{" "}
            <a href={`https://github.com/${github}`}>
              https://github.com/{github}
            </a>
          </div>
          <div className="flex flex-row items-center gap-3 mt-2">
            <SiVelog size="22" className="ml-0.5" />{" "}
            <a href={blog} className="ml-0.5">
              {blog}
            </a>
          </div>
          <div className="flex flex-row items-center gap-3 mt-2">
            <MdEmail size="26" className="" />{" "}
            <div className="mb-1">{email}</div>
          </div>
        </div>
      </div>
      <TechSelectMyPage
        open={isTechSelectOpen}
        onclose={() => setIsTechSelectOpen(false)}
      />
      <div className="flex-grow ">{children}</div>
    </div>
  );
}
