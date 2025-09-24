import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
  Stethoscope,
  Shield,
  Users,
  FileText,
  Clock,
  ChevronRight,
  CheckCircle,
  ArrowRight,
} from "lucide-react";
import { useNavigate } from "react-router-dom";

const Index = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: FileText,
      title: "Prontuário Digital",
      description: "Gerencie prontuários de forma segura e eficiente",
    },
    {
      icon: Users,
      title: "Múltiplos Usuários",
      description: "Admin, médicos, gestão e pacientes com níveis de acesso",
    },
    {
      icon: Shield,
      title: "Segurança Total",
      description: "Dados protegidos com criptografia avançada",
    },
    {
      icon: Clock,
      title: "Acesso 24/7",
      description: "Disponível a qualquer hora, em qualquer lugar",
    },
  ];

  const benefits = [
    "Redução de tempo de consulta",
    "Histórico médico completo",
    "Relatórios automatizados",
    "Integração com laboratórios",
    "Agenda inteligente",
    "Backup automático",
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-gray-50 to-blue-100">
      <section className="container mx-auto px-6 py-20">
        <div className="text-center max-w-4xl mx-auto">
          <div className="flex items-center justify-center mb-6">
            <div className="bg-blue-600 rounded-full p-4 shadow-lg transform transition-transform hover:scale-105">
              <Stethoscope className="h-12 w-12 text-white" />
            </div>
          </div>

          <h1 className="text-5xl sm:text-6xl font-extrabold mb-6 bg-gradient-to-r from-blue-600 to-teal-500 bg-clip-text text-transparent tracking-tight">
            AVICIA
          </h1>

          <h2 className="text-2xl sm:text-3xl font-semibold mb-6 text-gray-800">
            Prontuário Médico Eletrônico
          </h2>

          <p className="text-lg sm:text-xl text-gray-600 mb-8 leading-relaxed max-w-2xl mx-auto">
            Revolucione sua prática médica com nosso sistema completo de gestão
            de prontuários eletrônicos. Seguro, intuitivo e desenvolvido
            especialmente para profissionais da saúde.
          </p>

          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button
              size="lg"
              className="bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white text-lg px-8 py-3 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all duration-300"
              onClick={() => navigate("/login")}
            >
              Começar Agora
              <ArrowRight className="ml-2 h-5 w-5" />
            </Button>
            <Button
              size="lg"
              variant="outline"
              className="border-blue-600 text-blue-600 hover:bg-blue-50 hover:text-blue-700 text-lg px-8 py-3 rounded-lg font-semibold transition-all duration-300"
            >
              Saiba Mais
            </Button>
          </div>
        </div>
      </section>

      <section className="container mx-auto px-6 py-16">
        <div className="text-center mb-12">
          <h3 className="text-3xl font-bold text-blue-700 mb-4">
            Funcionalidades Principais
          </h3>
          <p className="text-gray-600 text-lg">
            Tudo que você precisa para uma gestão médica eficiente
          </p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {features.map((feature, index) => (
            <Card
              key={index}
              className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl hover:shadow-xl hover:-translate-y-1 transition-all duration-300"
            >
              <CardHeader className="text-center">
                <div className="bg-blue-100 rounded-full p-4 w-16 h-16 mx-auto mb-4 flex items-center justify-center">
                  <feature.icon className="h-8 w-8 text-blue-600" />
                </div>
                <CardTitle className="text-blue-700 text-xl font-semibold">
                  {feature.title}
                </CardTitle>
              </CardHeader>
              <CardContent>
                <CardDescription className="text-center text-gray-600">
                  {feature.description}
                </CardDescription>
              </CardContent>
            </Card>
          ))}
        </div>
      </section>

      <section className="container mx-auto px-6 py-16">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          <div>
            <h3 className="text-3xl font-bold text-blue-700 mb-6">
              Por que escolher o AVICIA?
            </h3>
            <p className="text-gray-600 text-lg mb-6">
              Nossa plataforma foi desenvolvida com foco na experiência do
              usuário e na segurança dos dados médicos.
            </p>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {benefits.map((benefit, index) => (
                <div key={index} className="flex items-center space-x-3">
                  <CheckCircle className="h-5 w-5 text-teal-500 flex-shrink-0" />
                  <span className="text-gray-700">{benefit}</span>
                </div>
              ))}
            </div>
          </div>

          <Card className="shadow-lg border border-blue-200/50 bg-white/95 backdrop-blur-sm rounded-xl">
            <CardHeader>
              <CardTitle className="text-blue-700">Tipos de Usuário</CardTitle>
              <CardDescription className="text-gray-600">
                Sistema com níveis de acesso personalizados
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              {[
                {
                  role: "Administrador",
                  desc: "Controle total do sistema",
                  color: "bg-red-500",
                },
                {
                  role: "Médico",
                  desc: "Acesso a prontuários e consultas",
                  color: "bg-blue-600",
                },
                {
                  role: "Gestão/Secretário",
                  desc: "Agendamento e relatórios",
                  color: "bg-teal-500",
                },
                {
                  role: "Paciente",
                  desc: "Visualização do próprio histórico",
                  color: "bg-green-500",
                },
              ].map((user, index) => (
                <div
                  key={index}
                  className="flex items-center justify-between p-3 rounded-lg bg-blue-50 hover:bg-blue-100 transition-colors duration-200"
                >
                  <div className="flex items-center space-x-3">
                    <Badge
                      className={`${user.color} text-white hover:${user.color}/90 transition-colors`}
                    >
                      {user.role}
                    </Badge>
                    <span className="text-gray-700">{user.desc}</span>
                  </div>
                  <ChevronRight className="h-4 w-4 text-gray-400" />
                </div>
              ))}
            </CardContent>
          </Card>
        </div>
      </section>

      <section className="container mx-auto px-6 py-16">
        <Card className="shadow-lg border border-blue-200/50 bg-gradient-to-r from-blue-50 to-teal-50 rounded-xl">
          <CardContent className="text-center py-12">
            <h3 className="text-3xl font-bold text-blue-700 mb-4">
              Pronto para começar?
            </h3>
            <p className="text-gray-600 text-lg mb-8 max-w-2xl mx-auto">
              Junte-se a centenas de profissionais que já confiam no AVICIA para
              gerenciar seus prontuários médicos.
            </p>
            <Button
              size="lg"
              className="bg-gradient-to-r from-blue-600 to-teal-500 hover:from-blue-700 hover:to-teal-600 text-white text-lg px-8 py-3 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all duration-300"
              onClick={() => navigate("/cadastro")}
            >
              Criar Conta Grátis
              <ArrowRight className="ml-2 h-5 w-5" />
            </Button>
          </CardContent>
        </Card>
      </section>
    </div>
  );
};

export default Index;
