"use client";
import { useEffect, useState, ReactElement } from "react";
import Image from 'next/image';
import { badgeDataMap ,badgeImageMap} from "@/data/badgeData";
import CheckExpire from "@/data/checkExpire";
import { isModuleNamespaceObject } from "util/types";
import { useMediaQuery } from "react-responsive";
function MyBadge() {
  const [acquired, setAcquired] = useState<string[]>([]);
  const [unAcquired, setUnAcquired] = useState<string[]>([]);

  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });
  useEffect(() => {
    CheckExpire();
    const token = sessionStorage.getItem('accessToken');
    fetch("https://spring.pickitup.online/users/badges",{
      method:"GET",
      headers:{
        Authorization: "Bearer "+token
      }
    })
      .then((res) => res.json())
      .then((res) => {
        const data = res;
        const tmpAcquired = [];
        const tmpUnAcquired = [];
        for (let i = 0; i < res.response.length; i++) {
          if (data.response[i].achieve === true) {
            const trueBN = badgeImageMap.get(data.response[i].badgeName);
            if (trueBN !== undefined) {
              tmpAcquired.push(trueBN);
            }
          } else {
            const falseBN = badgeImageMap.get(data.response[i].badgeName);
            if (falseBN !== undefined) tmpUnAcquired.push(falseBN);
          }
        }
        setUnAcquired(tmpUnAcquired), setAcquired(tmpAcquired);
      });
  }, []);
  useEffect(() => {
    const newAcq: ReactElement[] = [];
    const newUnAcq: ReactElement[] = [];
    for (let i = 0; i < acquired.length; i++) {
      newAcq.push(
        <div
          key={i}
          className="flex flex-col items-center justify-center h-32 w-32 mx-4"
        >
          <div className="flex items-center justify-center">
            <Image
              src={`/images/badge/${acquired[i]}.png`}
              width={100}
              height={100}
              alt={`${acquired[i]}`}
            />
          </div>
          <div className="flex items-center justify-center text-sm font-bold">
            {badgeDataMap.get(acquired[i])}
          </div>
        </div>
      );
    }
    setAcq(newAcq);
    for (let i = 0; i < unAcquired.length; i++) {
      newUnAcq.push(
        <div
          key={i}
          className="flex flex-col items-center justify-center h-32 w-32 mx-4"
        >
          <div className="h-[100px] w-[100px] flex items-center justify-center">
            {/* <Image src={`/images/badge/${unacquired[i]}.png`} width={100} height={100} alt="badge"/> */}
            <Image
              src="/images/badge/locked.png"
              width={50}
              height={50}
              alt="badge"
            />
          </div>
          <div className="flex items-center justify-center text-sm font-bold">
            {badgeDataMap.get(unAcquired[i])}
          </div>
        </div>
      );
    }
    setUnacq(newUnAcq);
  }, [acquired, unAcquired]);

  const [acq, setAcq] = useState<ReactElement[]>([]); //react 문 보낼것
  const [unacq, setUnacq] = useState<ReactElement[]>([]); //react 문 보낼것

  return (
    <div>
      <div
        className={`flex items-center justify-start mt-6 ${isMobile ? "ml-2" : ""}`}
      >
        <div
          className={`h-12 w-48 bg-[#CBFFC2] flex items-center justify-center rounded-lg font-bold `}
        >
          내가 획득한 뱃지
        </div>
      </div>
      <div className="flex items-center justify-start flex-wrap w-full">
        {acq}
      </div>
      <div
        className={`flex items-center justify-start mt-6 ${isMobile ? "ml-2" : ""}`}
      >
        <div className="h-12 w-48 bg-[#CBFFC2] flex items-center justify-center rounded-lg font-bold">
          획득 가능한 뱃지
        </div>
      </div>
      <div className="flex items-center justify-start flex-wrap w-full">
        {unacq}
      </div>
    </div>
  );
}
export default MyBadge;
