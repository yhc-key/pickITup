"use client"
import { Fragment, useState } from "react";
import { FaBookmark } from "react-icons/fa";

export default function MyPastAns() {
  const [myFavList, setMyFavList] = useState<string[]>([]);
  const pastAnsClickHandler = () => {
    
  }
  const exAnsClickHandler = () => {

  }
  return <table className="w-full">
        <thead>
      <tr className="text-left h-20 border-b-[1px]">
        <th className="pl-2 w-1/12">문제번호</th>
        <th className="pl-2 w-5/12">포지션명</th>
        <th className="pl-2 w-1/12 text-center">대분류</th>
        <th className="w-1/12 text-center">소분류</th>
        <th className="w-2/12 text-center">과거 제출 내역</th>
        <th className="w-2/12 text-center">예시 답변</th>
      </tr>
        </thead>
        <tbody>
      <tr className="h-20">
        <td >34</td>
        <td >React의 useEffect에 대해 설명해보시오. </td>
        <td className="font-bold text-center">FE</td>
        <td className="font-bold text-center" >React</td>
        <td className="text-center"><button type="button" onClick={pastAnsClickHandler} className="rounded-lg py-2 px-4 bg-f5green-300 text-white font-bold ">더보기</button></td>
        <td className="text-center"><button type="button" onClick={exAnsClickHandler} className="rounded-lg py-2 px-4 bg-f5red-300 text-white font-bold ">예시 답변</button></td>
      </tr>
      <tr className="h-20">
        <td >3</td>
        <td >Java에서 클래스와 인스턴스의 차이가 무엇입니까?</td>
        <td className="font-bold text-center">BE</td>
        <td className="font-bold text-center" >DB</td>
        <td className="text-center"><button type="button" onClick={pastAnsClickHandler} className="rounded-lg py-2 px-4 bg-f5green-300 text-white font-bold ">더보기</button></td>
        <td className="text-center"><button type="button" onClick={exAnsClickHandler} className="rounded-lg py-2 px-4 bg-f5red-300 text-white font-bold ">예시 답변</button></td>
      </tr>
        </tbody>
    </table>


}