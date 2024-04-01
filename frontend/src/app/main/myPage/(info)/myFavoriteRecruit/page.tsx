"use client";
import { Fragment, useState, MouseEvent } from "react";
import Link from "next/link";
import Image from "next/image";
import { FaBookmark } from "react-icons/fa";

import { Recruit } from "@/type/interface";

import { techDataMap } from "@/data/techData";
import useAuthStore, { AuthState } from "@/store/authStore";

export default function MyFavoriteRecruit() {
  const [myFavList, setMyFavList] = useState<string[]>([]);
  const bookmarks = useAuthStore((state: AuthState) => state.bookmarks);
  const setBookmarks = useAuthStore((state: AuthState) => state.setBookmarks);
  const techDataValues = Array.from(techDataMap.values());

  const deleteBookMark = async (
    event: MouseEvent<HTMLDivElement>,
    recruitId: number
  ) => {
    event.stopPropagation();
    const apiAddress = "https://spring.pickITup.online";
    try {
      const accessToken = sessionStorage.getItem("accessToken");
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

  const navigateToRecruitmentUrl = (url: string) => {
    window.open(url, "_blank");
  };

  return (
    <div>
      <div className="w-full">
        <div>
          <div className="flex text-left font-semibold items-center h-20 border-b-[1px]">
            <div className="w-2/12 px-3">회사명</div>
            <div className="w-4/12 px-3">포지션명</div>
            <div className="w-4/12 px-3">요구기술스택</div>
            <div className="w-1/12 px-3">종료일</div>
            <div className="w-1/12 px-3"></div>
          </div>
        </div>
        <div className="w-full">
          {bookmarks?.map((recruit: Recruit, index: number) => (
            <div
              key={index}
              className="w-full my-3 py-3 flex items-center h-28 text-sm text-left transition-all duration-300 ease-in rounded-md hover:bg-zinc-100 cursor-pointer"
              onClick={() => navigateToRecruitmentUrl(recruit.url)}
            >
              <div className="w-2/12 px-2">{recruit.company}</div>
              <div className="w-4/12 font-semibold">{recruit.title}</div>
              <div className="w-4/12">
                <div className="flex flex-wrap ">
                  {recruit.qualificationRequirements.map((tech, i) => {
                    let techTmp = tech.replace(/\s/g, "");
                    techTmp = techTmp.replace(/#/g, "Sharp");

                    if (
                      techDataValues.some((techDataValueArr) =>
                        techDataValueArr.includes(techTmp)
                      )
                    )
                      return (
                        <div key={i}>
                          <button
                            type="button"
                            key={index}
                            className={`m-1 flex flex-wrap border-f5gray-300 border py-1 pr-2 mb:pr-1 mb:py-0.5 rounded-2xl text-f5black-400 text-xs items-center`}
                          >
                            <Image
                              src={`/images/techLogo/${techTmp}.png`}
                              alt={techTmp}
                              width={22}
                              height={22}
                              className="mx-1"
                            />
                            {tech}
                          </button>
                        </div>
                      );
                  })}
                </div>
              </div>
              <div className="w-1/12">
                {recruit.dueDate[0]}-{recruit.dueDate[1]}-{recruit.dueDate[2]}
              </div>
              <div className=" w-1/12 text-lg text-f5green-300 text-center z-20 px-6 hover:scale-110 transition-all ease-in duration-300">
                <div onClick={(event) => deleteBookMark(event, recruit.id)}>
                  <FaBookmark />
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
