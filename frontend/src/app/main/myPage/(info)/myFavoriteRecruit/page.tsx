"use client";
import { Fragment, useState } from "react";
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

  const deleteBookMark = async (recruitId: number) => {
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
      <table className="w-full">
        <thead>
          <tr className="text-left h-20 border-b-[1px]">
            <th className="w-2/12 px-2">회사명</th>
            <th className="w-4/12 px-2">포지션명</th>
            <th className="w-4/12 px-2">요구기술스택</th>
            <th className="w-1/12 px-2">종료일</th>
            <th className="w-1/12 px-2"></th>
          </tr>
        </thead>
        <tbody>
          {bookmarks?.map((recruit: Recruit, index: number) => (
            <tr
              key={index}
              className="h-20 p-4 text-sm text-left transition-all duration-300 ease-in rounded-md hover:bg-zinc-100 hover:scale-105 cursor-pointer"
              onClick={() => navigateToRecruitmentUrl(recruit.url)}
            >
              <td className="px-2">{recruit.company}</td>
              <td>{recruit.title}</td>
              <td>
                <div className="flex">
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
                          <Image
                            src={`/images/techLogoEx/${techTmp}.png`}
                            alt={tech}
                            width="100"
                            height="100"
                            className="w-auto h-8"
                          />
                        </div>
                      );
                  })}
                </div>
              </td>
              <td>
                {recruit.dueDate[0]}-{recruit.dueDate[1]}-{recruit.dueDate[2]}
              </td>
              <td className="text-lg text-f5green-300 text-center">
                <button onClick={() => deleteBookMark(recruit.id)}>
                  <FaBookmark />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
