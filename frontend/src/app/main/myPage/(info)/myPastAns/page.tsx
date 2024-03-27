"use client";
import { Fragment, useEffect, useState } from "react";
import { useMediaQuery } from "react-responsive";
import { FaBookmark } from "react-icons/fa";

interface Interview {
  interviewId: number;
  mainCategory: string;
  subCategory: string;
  question: string;
  example: string;
  answer: string;
}

export default function MyPastAns() {
  const [myFavList, setMyFavList] = useState<Interview[]>([]);
  const apiUrl = "https://spring.pickitup.online/my/interviews";

  useEffect(() => {
    const fetchMyInterviewData = async () => {
      const accessToken = sessionStorage.getItem("accessToken");
      try {
        const resp: Response = await fetch(
          apiUrl, {
            headers: {
              Authorization: "Bearer " + accessToken,
            },
          }
        );
        const data: any = await resp.json();

        setMyFavList(data.response);
        console.log(data.response);
      } catch(error) {
        console.log(error);
      }
    };
    fetchMyInterviewData();
  },[apiUrl, setMyFavList])

  const pastAnsClickHandler = () => {};
  const exAnsClickHandler = () => {};
  return (
    <table className="w-full">
      <thead>
        <tr className="text-center h-20 border-b-[1px]">
          <th className="pl-2 w-1/12">문제번호</th>
          <th className="pl-2 w-1/12 text-center">대분류</th>
          <th className="w-1/12 text-center">소분류</th>
          <th className="pl-2 w-5/12">문제</th>
          <th className="w-2/12 text-center">과거 제출 내역</th>
          <th className="w-2/12 text-center">예시 답변</th>
        </tr>
      </thead>
      <tbody>
  {myFavList.map((interview: Interview, index: number) => (
    <tr key={index} className="h-20 text-center">
      <td>{interview.interviewId}</td>
      <td className="font-bold">{interview.mainCategory}</td>
      <td className="font-bold">{interview.subCategory}</td>
      <td>{interview.question}</td>
      <td className="text-center">
        <button
          type="button"
          onClick={pastAnsClickHandler}
          className="rounded-lg py-2 px-4 bg-f5green-300 text-white font-bold "
        >
          더보기
        </button>
      </td>
      <td className="text-center">
        <button
          type="button"
          onClick={exAnsClickHandler}
          className="rounded-lg py-2 px-4 bg-f5red-300 text-white font-bold "
        >
          예시 답변
        </button>
      </td>
    </tr>
  ))}
</tbody>
    </table>
  );
}
