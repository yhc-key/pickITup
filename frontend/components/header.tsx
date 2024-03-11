
import Link from "next/link";
import { usePathname } from "next/navigation";

interface LinkType {
  name: string;
  href: string;
}

export default function Header() {
  const navLinks: LinkType[] = [
    { name: "채용공고", href: "/recruit" },
    { name: "기술블로그", href: "/techBlog" },
    { name: "미니 게임", href: "/game" },
    { name: "면접 대비", href: "/interview" },
  ];

  const pathname = usePathname();

  return (
    <div className="flex text-center justify-center">
      {navLinks.map((link: LinkType) => {
        const isActive = pathname.startsWith(link.href);
        return (
          <Link
            href={link.href}
            key={link.name}
            className={isActive ? "font-bold mr-4" : "text-blue-500 mr-4"}
          >
            {link.name}
          </Link>
        );
      })}
    </div>
  );
}
