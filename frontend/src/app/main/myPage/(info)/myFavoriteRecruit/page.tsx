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

  return (
    <div>
      <table className="w-full">
        <thead>
          <tr className="text-left h-20 border-b-[1px]">
            <th className="pl-2 w-2/12">회사명</th>
            <th className="pl-2 w-4/12">포지션명</th>
            <th className="pl-2 w-4/12">요구기술스택</th>
            <th className="pl-2 w-1/12">종료일</th>
            <th className="pl-2 w-1/12"></th>
          </tr>
        </thead>
        <tbody>
          {bookmarks?.map((recruit: Recruit, index: number) => (
            <tr
              key={index}
              className="h-20 text-left text-sm transition-all ease-in duration-200"
            >
              <td>{recruit.company}</td>
              <td>
                <Link
                  href={`${recruit.url}`}
                  className="hover:text-sky-600 hover:underline"
                >
                  {recruit.title}
                </Link>
              </td>
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
                            src={`/images/techLogo/${techTmp}.png`}
                            alt={tech}
                            width="100"
                            height="100"
                            className="h-8 w-auto"
                          />
                        </div>
                      );
                  })}
                </div>
              </td>
              <td>
                {recruit.dueDate[0]}-{recruit.dueDate[1]}-{recruit.dueDate[2]}
              </td>
              <td className="text-lg text-f5green-300">
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
