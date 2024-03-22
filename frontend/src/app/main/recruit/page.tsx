"use client";

import { useEffect } from "react";

const apiAddress = "https://spring.pickITup.online/recruit";

export default function Recruit() {
  useEffect(() => {
    const fetchRecruits = async () => {
      try {
        const res = await fetch(`${apiAddress}?page=0&size=1&sort=null`);
        if (!res.ok) {
          throw new Error("잘못된 페치");
        }
        const data = await res.json();
        console.log(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchRecruits();
  }, []);
  return (
    <>
      <div>리크룻 설명 페이지</div>
    </>
  );
}
