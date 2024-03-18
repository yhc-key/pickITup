"use client";
import { Fragment, useState } from "react";
import { cloneDeep } from "lodash";
import { FaBookmark } from "react-icons/fa";

export default function MyFavoriteRecruit() {
  const [myFavList, setMyFavList] = useState<string[]>([]);
  return (
    <table className="w-full">
      <thead>
        <tr className="text-left h-20 border-b-[1px]">
          <th className="pl-2 w-2/12">회사명</th>
          <th className="pl-2 w-6/12">포지션명</th>
          <th className="pl-2 w-1/12">연차</th>
          <th className="pl-2 w-2/12">종료일</th>
          <th className="pl-2 w-1/12"></th>
        </tr>
      </thead>
      <tbody>
        <tr className="h-20">
          <td>아인시스에이아이랩</td>
          <td>[2년 이상] Product Software Engineer </td>
          <td>주니어</td>
          <td>2024-03-28</td>
          <td className=" text-lg text-f5green-300">
            <button onClick={() => setMyFavList([])}>
              <FaBookmark />
            </button>
          </td>
        </tr>
        <tr className="h-20">
          <td>에스비티(SBT)</td>
          <td>[3년 이상] Product Software Engineer</td>
          <td>경력</td>
          <td>2024-04-21</td>
          <td className="text-lg text-f5green-300">
            <button onClick={() => setMyFavList([])}>
              <FaBookmark />
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  );
}
