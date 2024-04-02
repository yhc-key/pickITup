"use client";
import { techData2 } from "@/data/techData";
import { Fragment, MouseEvent, useEffect, useState } from "react";
import Link from "next/link";
import Image from "next/image";
import { FaBookmark } from "react-icons/fa";

import { RecommendRecruit, Recruit } from "@/type/interface";

import { techDataMap } from "@/data/techData";
import useAuthStore, { AuthState } from "@/store/authStore";
import { FaRegBookmark } from "react-icons/fa6";
import { useMediaQuery } from "react-responsive";
import { ClimbingBoxLoader } from "react-spinners";
import { useRouter } from "next/navigation";
import LoginNeed from "@/components/loginNeed";
import { access } from "fs";
import CheckExpire from "@/data/checkExpire";

const apiAddress = "https://spring.pickITup.online";
export default function MyFavoriteRecruit() {
  const [myRecommendList, setMyRecommendList] = useState<RecommendRecruit[]>(
    []
  );
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const bookmarks = useAuthStore((state) => state.bookmarks);
  const isLoggedIn = useAuthStore((state) => state.isLoggedIn);
  const [recommendLoading, setRecommendLoading] = useState<boolean>(true);
  const { setBookmarks } = useAuthStore();
  const techDataValues = Array.from(techDataMap.values());
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const router = useRouter();

  const navigateToRecruitmentUrl = (url: string) => {
    window.open(url, "_blank");
  };

  const bookMarkHandler = (
    event: MouseEvent<HTMLDivElement>,
    recruitId: number
  ) => {
    event.stopPropagation();
    checkBookmark(recruitId)
      ? deleteBookMark(recruitId)
      : addBookMark(recruitId);
  }; // 북마크 추가 혹은 제거

  const deleteBookMark = async (recruitId: number) => {
    try {
      const res = await fetch(
        `${apiAddress}/users/scraps/recruit?recruitId=${recruitId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        }
      );
      const data = await res.json();
      console.log(data);
      if (bookmarks !== undefined) {
        setBookmarks(
          [...bookmarks].filter((bookmark) => bookmark.id != recruitId)
        );
      }
    } catch (error) {
      console.error(error);
    }
  }; //북마크 삭제 함수

  const addBookMark = async (recruitId: number) => {
    try {
      const res = await fetch(
        `${apiAddress}/users/scraps/recruit?recruitId=${recruitId}`,
        {
          method: "POST",
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        }
      );
      const data = await res.json();
      console.log(data);

      const scrapRes = await fetch(`${apiAddress}/users/scraps/recruit`, {
        headers: {
          Authorization: "Bearer " + accessToken,
        },
      });
      const bookmarkData = await scrapRes.json();
      setBookmarks(bookmarkData?.response);
    } catch (error) {
      console.error(error);
    }
  }; //북마크 추가 함수

  const checkBookmark = (recruitId: number) => {
    let toggle = false;
    bookmarks?.some((bookmarkedRecruit) => {
      if (bookmarkedRecruit.id === recruitId) {
        toggle = true;
        return;
      }
    });
    return toggle;
  }; //북마크 여부 확인 함수
  useEffect(() => {
    CheckExpire();
    setAccessToken(sessionStorage.getItem("accessToken"));
  }, []); //토큰 저장

  useEffect(() => {
    const fetchRecommends = async () => {
      try {
        const res = await fetch(`${apiAddress}/users/recommend/recruit`, {
          headers: {
            Authorization: "Bearer " + accessToken,
          },
        });
        const data = await res.json();
        console.log(data.response);
        setMyRecommendList(data.response);
        setRecommendLoading(false);
      } catch (error) {
        console.error(error);
      }
    }; // 추천 공고 가져오기
    if (accessToken) {
      fetchRecommends();
    }
  }, [accessToken]); //토큰 저장되면 그걸로 불러오기

  return (
    <Fragment>
      {isLoggedIn ? (
        <div>
          {!recommendLoading ? (
            <div className={`flex justify-center ${isMobile ? "" : "mx-40"}`}>
              <table className="w-full">
                <thead>
                  <tr
                    className={`text-left h-20 border-b-[1px] ${isMobile ? "text-sm" : ""}`}
                  >
                    <th className="w-2/12 px-1">
                      {isMobile ? "회사" : "회사명"}{" "}
                    </th>
                    <th className="w-4/12 px-1">
                      {isMobile ? "포지션" : "포지션명"}
                    </th>
                    <th className="w-3/12 px-1">
                      {isMobile ? "기술스택" : "요구기술스택"}
                    </th>
                    <th className="w-1/12 text-center">거리</th>
                    <th className="w-1/12 text-center">기한</th>
                    <th className="w-1/12 px-2"></th>
                  </tr>
                </thead>
                <tbody>
                  {myRecommendList?.map(
                    (recruit: RecommendRecruit, index: number) => (
                      <tr
                        key={index}
                        className={`h-20 p-4 text-sm text-left transition-all duration-300 ease-in rounded-md hover:bg-zinc-100 hover:scale-105 cursor-pointer `}
                        onClick={() => navigateToRecruitmentUrl(recruit.url)}
                      >
                        <td className={`${isMobile ? "px-1" : "px-2"}`}>
                          {recruit.company}
                        </td>
                        <td>{recruit.title}</td>
                        <td>
                          <div className="flex flex-wrap gap-1">
                            {recruit.qualificationRequirements.map(
                              (tech, i) => {
                                let techTmp = tech.replace(/\s/g, "");
                                let haveTech =
                                  recruit.intersection.includes(tech);
                                techTmp = techTmp.replace(/#/g, "Sharp");
                                if (isMobile) {
                                  return (
                                    <>
                                      {!techData2.includes(tech) ? (
                                        <Image
                                          src={`/images/techLogo/${techTmp}.png`}
                                          alt={tech}
                                          width={22}
                                          height={22}
                                          priority={true}
                                          key={i}
                                          className={`border-f5gray-300 border rounded-full ${haveTech ? "border-f5green-300 border-1 scale-105" : ""}`}
                                        />
                                      ) : (
                                        <Image
                                          src={`/images/ITUlogo.png`}
                                          alt={tech}
                                          width={22}
                                          height={22}
                                          priority={true}
                                          key={i}
                                          className={`border-f5gray-300 border rounded-full ${haveTech ? "border-f5green-300 border-1 scale-105" : ""}`}
                                        />
                                      )}
                                    </>
                                  );
                                }
                                return (
                                  <div
                                    key={i}
                                    className={`flex flex-row border-f5gray-300 border py-1 pr-2 mb:pr-1 mb:py-0.5 rounded-2xl text-f5black-400 text-xs items-center ${haveTech ? "border-f5green-300 border-1 scale-105" : ""}`}
                                  >
                                    {!techData2.includes(tech) ? (
                                      <Image
                                        src={`/images/techLogo/${techTmp}.png`}
                                        alt={tech}
                                        width={22}
                                        height={22}
                                        priority={true}
                                        className="mx-1"
                                      />
                                    ) : (
                                      <Image
                                        src={`/images/ITUlogo.png`}
                                        alt={tech}
                                        width={22}
                                        height={22}
                                        priority={true}
                                        className="mx-1"
                                      />
                                    )}
                                    {tech}
                                  </div>
                                );
                              }
                            )}
                          </div>
                        </td>
                        <td className="text-center">
                          {isMobile
                            ? Math.ceil(recruit.distance) + "km"
                            : "약 " + Math.ceil(recruit.distance) + "km"}
                        </td>
                        <td
                          className={`text-center ${isMobile ? "text-xs" : ""}`}
                        >
                          {recruit.dueDate[0] == 2100
                            ? "상시채용"
                            : isMobile
                              ? recruit.dueDate[1] + "-" + recruit.dueDate[2]
                              : recruit.dueDate[0] +
                                "-" +
                                recruit.dueDate[1] +
                                "-" +
                                recruit.dueDate[2]}
                        </td>
                        <td className="justify-center text-lg text-f5green-300 duration-300 ease-in-out hover:scale-125">
                          <div
                            onClick={(event) =>
                              bookMarkHandler(event, recruit.recruitId)
                            }
                            className={`${isMobile ? "" : "text-xl"} flex justify-center text-f5green-300 duration-300 ease-in-out hover:scale-105 `}
                          >
                            {checkBookmark(recruit.recruitId) ? (
                              <FaBookmark />
                            ) : (
                              <FaRegBookmark />
                            )}
                          </div>
                        </td>
                      </tr>
                    )
                  )}
                </tbody>
              </table>
            </div>
          ) : (
            <div
              className={`flex flex-col justify-center h-[60vh] items-center ${isLoggedIn && !recommendLoading ? "hidden" : ""}`}
            >
              <Fragment>
                <ClimbingBoxLoader size={20} />
                <div className="text-xl ml-16 mt-3">추천 중입니다!!</div>
              </Fragment>
            </div>
          )}
        </div>
      ) : (
        <LoginNeed />
      )}
    </Fragment>
  );
}
